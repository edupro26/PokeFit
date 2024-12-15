package pt.ul.fc.cm.pokefit.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import pt.ul.fc.cm.pokefit.data.remote.PokeApi
import pt.ul.fc.cm.pokefit.data.repository.AuthRepositoryImpl
import pt.ul.fc.cm.pokefit.utils.Constants
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.data.repository.PokemonRepositoryImpl
import pt.ul.fc.cm.pokefit.data.repository.UserRepositoryImpl
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.POKEAPI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApi): PokemonRepository {
        return PokemonRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(
            Firebase.auth
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl(
            Firebase.firestore
        )
    }

}
