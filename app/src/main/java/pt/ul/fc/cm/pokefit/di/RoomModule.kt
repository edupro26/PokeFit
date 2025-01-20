package pt.ul.fc.cm.pokefit.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.ul.fc.cm.pokefit.data.repository.RoutePointRepositoryImpl
import pt.ul.fc.cm.pokefit.data.room.LocalDatabase
import pt.ul.fc.cm.pokefit.data.room.dao.RoutePointDao
import pt.ul.fc.cm.pokefit.domain.repository.RoutePointRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "room_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoutePointDao(database: LocalDatabase): RoutePointDao {
        return database.routePointDao
    }

    @Provides
    @Singleton
    fun provideRoutePointRepository(db: LocalDatabase): RoutePointRepository {
        return RoutePointRepositoryImpl(db.routePointDao)
    }
}

