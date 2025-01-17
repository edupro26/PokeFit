package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.utils.Response

interface PokemonRepository {

    suspend fun savePokemon(uid: String, pokemon: Pokemon): Response<Unit>

    suspend fun unlockPokemon(pokemon: Pokemon, amount: Int, uid: String): Response<Unit>

    suspend fun selectPokemon(id: Int, uid: String): Response<Unit>

    suspend fun getAllUserPokemon(uid: String): Response<List<Pokemon>>

    suspend fun getUserPokemon(id: Int, uid: String): Response<Pokemon>

    suspend fun getPokemonListApi(limit: Int): List<Pokemon>

    suspend fun getPokemonInfoApi(id: Int): Pokemon

}