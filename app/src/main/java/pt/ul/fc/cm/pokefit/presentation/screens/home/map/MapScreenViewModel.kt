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
        14f // Zoom level
    )
    private val _routePoints = MutableStateFlow<List<RoutePoint>>(emptyList())
    val routePoints: StateFlow<List<RoutePoint>> = _routePoints
    private val _totalDistance = MutableStateFlow(0.0)
    val totalDistance: StateFlow<Double> = _totalDistance

    init {
        viewModelScope.launch {
            routePointDao.getAllRoutePoints().collect { points ->
                // Filtrar pontos pela velocidade
                val filteredPoints = points.filter { it.speed <= 6 } // 21.6 km/h
                _routePoints.value = filteredPoints
                _totalDistance.value = calculateTotalDistance(filteredPoints)
            }
        }
    }

    fun saveRoutePoint(location: Location) {
        viewModelScope.launch {
            val lastPoint = routePointDao.getLastRoutePoint()

            // Calcular a distância do último ponto ao novo ponto
            val isNewRoute = if (lastPoint != null) {
                val distance = calculateDistance(
                    lat1 = lastPoint.latitude,
                    lon1 = lastPoint.longitude,
                    lat2 = location.latitude,
                    lon2 = location.longitude
                )
                distance > 500 // Define o limite de 500 metros
            } else {
                true // Se não houver pontos, é uma nova rota
            }

            // Determinar o novo routeId
            val newRouteId = if (isNewRoute) {
                (lastPoint?.routeId ?: 0) + 1 // Nova rota
            } else {
                lastPoint?.routeId ?: 0 // Mesma rota
            }

            // Inserir o novo ponto no banco de dados
            routePointDao.insertRoutePoint(
                RoutePoint(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    timestamp = System.currentTimeMillis(),
                    speed = location.speed,
                    routeId = newRouteId
                )
            )
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // Raio da Terra em metros

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c // Retorna a distância em metros
    }

    private fun calculateTotalDistance(routePoints: List<RoutePoint>): Double {
        if (routePoints.size < 2) return 0.0 // Sem pontos ou apenas um ponto

        var totalDistance = 0.0
        for (i in 1 until routePoints.size) {
            val prev = routePoints[i - 1]
            val curr = routePoints[i]
            totalDistance += calculateDistance(
                prev.latitude, prev.longitude,
                curr.latitude, curr.longitude
            )
        }
        return totalDistance // Retorna em metros
    }

    fun clearAllPoints() {
        viewModelScope.launch {
            routePointDao.clearAllRoutePoints()
        }
    }
}



