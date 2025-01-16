package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.utils.Response

interface PokemonRepository {

    val apiCache: List<Pokemon>

    suspend fun savePokemon(uid: String, pokemon: Pokemon): Response<Unit>

    suspend fun getUserPokemon(uid: String): Response<List<Pokemon>>

    suspend fun getPokemonListApi(limit: Int): List<Pokemon>

    suspend fun getPokemonInfoApi(name: String): Pokemon

}