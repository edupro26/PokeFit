package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint
import pt.ul.fc.cm.pokefit.domain.repository.RoutePointRepository
import pt.ul.fc.cm.pokefit.domain.services.LocationTrackingService
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class MapViewModel @Inject constructor(
    private val routePointRepository: RoutePointRepository
) : ViewModel() {
    val isTracking: StateFlow<Boolean> = LocationTrackingService.isTracking
    val currentLocation: StateFlow<Location?> = LocationTrackingService.currentLocation

    val initialCameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(
        LatLng(38.736946, -9.142685), // Example: Latitude and Longitude of Lisbon
        16f // Zoom level
    )

    val routePoints: StateFlow<List<RoutePoint>> = routePointRepository.getAllRoutePoints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalDistance: StateFlow<Double> = routePoints
        .map { calculateTotalDistance(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun clearAllRoutePoints() {
        viewModelScope.launch {
            routePointRepository.clearAllRoutePoints()
        }
    }

    private fun calculateTotalDistance(points: List<RoutePoint>): Double {
        if (points.size < 2) return 0.0

        return points.zipWithNext { a, b ->
            calculateDistance(a.latitude, a.longitude, b.latitude, b.longitude)
        }.sum()
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // Earth's radius in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c // Returns the distance in meters
    }
}
