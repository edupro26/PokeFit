package pt.ul.fc.cm.pokefit.data.remote

import pt.ul.fc.cm.pokefit.data.remote.dtos.PokemonDetailDto
import pt.ul.fc.cm.pokefit.data.remote.dtos.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailDto

}