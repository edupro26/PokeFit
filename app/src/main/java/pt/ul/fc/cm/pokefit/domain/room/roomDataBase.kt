package pt.ul.fc.cm.pokefit.domain.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
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
    suspend fun clearAllRoutePoints() // New function to delete all points
}

@Database(entities = [RoutePoint::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routePointDao(): RoutePointDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRoutePointDao(db: AppDatabase): RoutePointDao {
        return db.routePointDao()
    }
}