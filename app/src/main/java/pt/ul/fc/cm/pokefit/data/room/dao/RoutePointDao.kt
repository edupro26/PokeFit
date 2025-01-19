package pt.ul.fc.cm.pokefit.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint

@Dao
interface RoutePointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutePoint(routePoint: RoutePoint)

    @Query("SELECT * FROM route_points ORDER BY timestamp ASC")
    fun getAllRoutePoints(): Flow<List<RoutePoint>>

    @Query("SELECT * FROM route_points ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastRoutePoint(): RoutePoint?

    @Query("DELETE FROM route_points")
    suspend fun clearAllRoutePoints()

}