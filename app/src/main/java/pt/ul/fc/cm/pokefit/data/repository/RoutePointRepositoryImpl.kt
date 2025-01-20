package pt.ul.fc.cm.pokefit.data.repository

import kotlinx.coroutines.flow.Flow
import pt.ul.fc.cm.pokefit.data.room.dao.RoutePointDao
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint
import pt.ul.fc.cm.pokefit.domain.repository.RoutePointRepository
import javax.inject.Inject

class RoutePointRepositoryImpl @Inject constructor(
    private val dao: RoutePointDao
) : RoutePointRepository {

    override suspend fun insertRoutePoint(routePoint: RoutePoint) {
        dao.insertRoutePoint(routePoint)
    }

    override fun getAllRoutePoints(): Flow<List<RoutePoint>> {
        return dao.getAllRoutePoints()
    }

    override suspend fun getLastRoutePoint(): RoutePoint? {
        return dao.getLastRoutePoint()
    }

    override suspend fun clearAllRoutePoints() {
        dao.clearAllRoutePoints()
    }

}

