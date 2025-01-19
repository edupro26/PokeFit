package pt.ul.fc.cm.pokefit.domain.repository

import kotlinx.coroutines.flow.Flow
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint

interface RoutePointRepository {

    suspend fun insertRoutePoint(routePoint: RoutePoint)

    fun getAllRoutePoints(): Flow<List<RoutePoint>>

    suspend fun getLastRoutePoint(): RoutePoint?

    suspend fun clearAllRoutePoints()

}