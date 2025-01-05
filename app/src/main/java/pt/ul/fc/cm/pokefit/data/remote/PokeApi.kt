package pt.ul.fc.cm.pokefit.data.remote

import pt.ul.fc.cm.pokefit.data.remote.dtos.PokemonDto
import pt.ul.fc.cm.pokefit.data.remote.dtos.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonDto

}