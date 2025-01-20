package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import pt.ul.fc.cm.pokefit.domain.services.LocationTrackingService
import pt.ul.fc.cm.pokefit.presentation.screens.home.map.components.MapTopBar

@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isTracking by mapViewModel.isTracking.collectAsState()
    val totalDistance by mapViewModel.totalDistance.collectAsState()
    var showCongratsCard by remember { mutableStateOf(false) }
    requestCurrentLocation(context)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { MapTopBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                GoogleMapView(
                    mapViewModel = mapViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isTracking) {
                        DistanceDisplay(mapViewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                        StopButton {
                            stopLocationTrackingService(context)
                            showCongratsCard = true
                        }
                    } else {
                        TrackButton {
                            startLocationTrackingService(context)
                            mapViewModel.clearAllRoutePoints()
                        }
                    }
                }
            }
        }

        if (showCongratsCard) {
            CongratsCard(
                totalDistance = totalDistance,
                onDismiss = { showCongratsCard = false }
            )
        }
    }
}

@Composable
fun TrackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text("Track your journey!")
    }
}

@Composable
fun StopButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text("Stop journey")
    }
}

private fun startLocationTrackingService(context: Context) {
    val serviceIntent = Intent(context, LocationTrackingService::class.java)
    context.startService(serviceIntent)
}

private fun stopLocationTrackingService(context: Context) {
    val serviceIntent = Intent(context, LocationTrackingService::class.java)
    context.stopService(serviceIntent)
}

private fun requestCurrentLocation(context: Context) {
    val serviceIntent = Intent(context, LocationTrackingService::class.java).apply {
        action = LocationTrackingService.ACTION_GET_CURRENT_LOCATION
    }
    context.startService(serviceIntent)
}

@Composable
fun GoogleMapView(
    mapViewModel: MapViewModel,
    modifier: Modifier
) {
    val routePoints by mapViewModel.routePoints.collectAsState()
    val currentLocation by mapViewModel.currentLocation.collectAsState()
    val groupedRoutes = routePoints.groupBy { it.routeId }

    val cameraPositionState = rememberCameraPositionState {
        position = currentLocation?.let {
            CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude),
                16f
            )
        } ?: mapViewModel.initialCameraPosition
    }

    // Update camera position when location changes
    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(
                    LatLng(location.latitude, location.longitude)
                )
            )
        }
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = currentLocation != null
            )
        ) {
            groupedRoutes.forEach { (_, points) ->
                if (points.isNotEmpty()) {
                    Polyline(
                        points = points.map {
                            LatLng(it.latitude, it.longitude)
                        },
                        color = MaterialTheme.colorScheme.primary,
                        width = 25f
                    )
                }
            }
        }
    }
}

@Composable
fun DistanceDisplay(mapViewModel: MapViewModel) {
    val totalDistance by mapViewModel.totalDistance.collectAsState()
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 3.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Total Distance: %.2f km".format(totalDistance / 1000),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CongratsCard(
    totalDistance: Double,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(
//                    text = "Congratulations!",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer,
//                    textAlign = TextAlign.Center
//                )
//                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You covered a total distance of %.2f km!".format(totalDistance / 1000),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("OK")
                }
            }
        }
    }
}
