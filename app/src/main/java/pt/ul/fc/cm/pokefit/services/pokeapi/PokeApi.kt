package pt.ul.fc.cm.pokefit.services.pokeapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Pokemon

    companion object {
        fun create(): PokeApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(PokeApi::class.java)
        }
    }
}


/**
 * Data classes to retrieve a list of pokemon from the PokeApi
 */
data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem>
)

data class PokemonItem(
    val name: String,
    val url: String
)


/**
 * Data classes to retrieve a pokemon from the PokeApi
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<TypeItem>
)

data class TypeItem(
    val slot: Int,
    val type: Type
)

data class Type( val name: String )