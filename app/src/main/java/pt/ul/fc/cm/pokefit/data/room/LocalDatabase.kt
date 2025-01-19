package pt.ul.fc.cm.pokefit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.ul.fc.cm.pokefit.domain.model.RoutePoint
import pt.ul.fc.cm.pokefit.data.room.dao.RoutePointDao

@Database(
    entities = [RoutePoint::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract val routePointDao: RoutePointDao

}