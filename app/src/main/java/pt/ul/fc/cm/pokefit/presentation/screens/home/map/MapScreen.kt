package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import pt.ul.fc.cm.pokefit.presentation.screens.home.map.components.LocationUpdate
import pt.ul.fc.cm.pokefit.presentation.screens.home.map.components.MapTopBar

@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { MapTopBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationUpdate(mapViewModel = mapViewModel)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 18.dp,
                        end = 18.dp,
                        bottom = 18.dp
                    )
            ) {
                GoogleMapView(
                    mapViewModel = mapViewModel,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(24.dp))
                DistanceDisplay(mapViewModel)
            }
        }
    }
}

@Composable
fun GoogleMapView(
    mapViewModel: MapViewModel,
    modifier: Modifier
) {
    val routePoints by mapViewModel.routePoints.collectAsState()
    val groupedRoutes = routePoints.groupBy { it.routeId }
    val cameraPositionState = rememberCameraPositionState {
        position = mapViewModel.initialCameraPosition
    }
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState
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