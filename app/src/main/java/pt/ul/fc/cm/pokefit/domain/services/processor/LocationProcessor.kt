package pt.ul.fc.cm.pokefit.domain.services.processors

import android.location.Location
import pt.ul.fc.cm.pokefit.data.repository.RoutePointRepositoryImpl
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint
import pt.ul.fc.cm.pokefit.data.room.dao.RoutePointDao
import pt.ul.fc.cm.pokefit.domain.repository.RoutePointRepository
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class LocationProcessor @Inject constructor(
    private val routePointRepositoryImpl: RoutePointRepositoryImpl
) {

    suspend fun saveRoutePoint(location: Location) {
        val lastPoint = routePointRepositoryImpl.getLastRoutePoint()

        // Calculate whether this is a new route
        val isNewRoute = if (lastPoint != null) {
            val distance = calculateDistance(
                lat1 = lastPoint.latitude,
                lon1 = lastPoint.longitude,
                lat2 = location.latitude,
                lon2 = location.longitude
            )
            distance > 500 // Define the 500m threshold for new routes
        } else {
            true // If no points exist, assume this is a new route
        }

        // Determine the route ID
        val newRouteId = if (isNewRoute) {
            (lastPoint?.routeId ?: 0) + 1 // New route
        } else {
            lastPoint?.routeId ?: 0 // Same route
        }

        // Save the new route point
        routePointRepositoryImpl.insertRoutePoint(
            RoutePoint(
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = System.currentTimeMillis(),
                speed = location.speed,
                routeId = newRouteId
            )
        )
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // Earth's radius in meters

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c // Returns the distance in meters
    }
}