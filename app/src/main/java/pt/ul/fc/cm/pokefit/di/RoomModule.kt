package pt.ul.fc.cm.pokefit.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.ul.fc.cm.pokefit.data.repository.RoutePointRepositoryImpl
import pt.ul.fc.cm.pokefit.data.repository.StepCountRepositoryImpl
import pt.ul.fc.cm.pokefit.data.room.LocalDatabase
import pt.ul.fc.cm.pokefit.domain.repository.RoutePointRepository
import pt.ul.fc.cm.pokefit.domain.repository.StepCountRepository
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideRoutePointRepository(db: LocalDatabase): RoutePointRepository {
        return RoutePointRepositoryImpl(db.routePointDao)
    }

    @Provides
    @Singleton
    fun provideStepCountRepository(db: LocalDatabase): StepCountRepository {
        return StepCountRepositoryImpl(db.stepCountDao)
    }

}