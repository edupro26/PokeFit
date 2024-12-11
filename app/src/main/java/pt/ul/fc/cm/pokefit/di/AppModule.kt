package pt.ul.fc.cm.pokefit.di

import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import pt.ul.fc.cm.pokefit.data.remote.PokeApi
import pt.ul.fc.cm.pokefit.utils.Constants
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.data.repository.PokemonRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object AppModule {

    @Provides
    @Singleton
    fun injectPokeApi(): PokeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.POKEAPI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun injectPokemonRepository(api: PokeApi): PokemonRepository {
        return PokemonRepositoryImpl(api)
    }

}
