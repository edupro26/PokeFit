package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            MapTopBar(scrollBehavior, navigate)
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
            GoogleMapView(mapViewModel = mapViewModel)
        }
    }
}

@Composable
fun GoogleMapView(mapViewModel: MapViewModel) {
    val cameraPositionState = rememberCameraPositionState {
        position = mapViewModel.initialCameraPosition
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        mapViewModel.markers.forEach { marker ->
            Marker(
                state = rememberMarkerState(position = marker.position)
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
