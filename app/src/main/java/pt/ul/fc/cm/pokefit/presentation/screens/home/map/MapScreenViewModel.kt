package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    val initialCameraPosition = CameraPosition.fromLatLngZoom(
        LatLng(38.736946, -9.142685), // Example: Lisbon, Portugal
        12f // Zoom level
    )

    val markers = listOf(
        MarkerState(
            position = LatLng(38.736946, -9.142685),
        ),
        MarkerState(
            position = LatLng(38.707750, -9.136592),
        )
    )
}
