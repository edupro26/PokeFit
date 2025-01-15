package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.android.gms.maps.model.LatLng
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen


@Composable
fun MapScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        mapViewModel.clearAllPoints()
    }

    // Inicia as atualizações de localização
    LocationUpdate(mapViewModel = mapViewModel)

    // Conteúdo do mapa
    MapContent(navController, navigate, mapViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapContent(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    mapViewModel: MapViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            MapTopBar(TopAppBarDefaults.pinnedScrollBehavior(), navigate)
        },
        bottomBar = {
            BottomAppBar(navController, navigate)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GoogleMapView(mapViewModel)
        }
    }
}

@Composable
fun GoogleMapView(mapViewModel: MapViewModel) {
    val routePoints by mapViewModel.routePoints.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = mapViewModel.initialCameraPosition
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        if (routePoints.isNotEmpty()) {
            Polyline(
                points = routePoints.map { LatLng(it.latitude, it.longitude) } ,
                color = MaterialTheme.colorScheme.primary,
                width = 25f
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MapTopBar(scrollBehavior: TopAppBarScrollBehavior, navigate: (String, Boolean) -> Unit) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        firstIcon = R.drawable.ic_top_map,
        firstDescription = "Map",
        onFirstIconClick = { navigate(Screen.Map.route, false) },
        secondIcon = R.drawable.ic_top_tasks,
        secondDescription = "Goals",
        onSecondIconClick = { /*TODO*/ },
    )
}
