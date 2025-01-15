package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.location.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint
import pt.ul.fc.cm.pokefit.domain.room.RoutePointDao
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val routePointDao: RoutePointDao
) : ViewModel() {
    val initialCameraPosition = CameraPosition.fromLatLngZoom(
        LatLng(38.736946, -9.142685), // Example: Lisbon, Portugal
        12f // Zoom level
    )
    private val _routePoints = MutableStateFlow<List<RoutePoint>>(emptyList())
    val routePoints: StateFlow<List<RoutePoint>> = _routePoints

    init {
        viewModelScope.launch {
            routePointDao.getAllRoutePoints().collect { points ->
                _routePoints.value = points
            }
        }
    }

    fun saveRoutePoint(location: Location) {
        viewModelScope.launch {
            routePointDao.insertRoutePoint(
                RoutePoint(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun clearAllPoints() {
        viewModelScope.launch {
            routePointDao.clearAllRoutePoints()
        }
    }
}



