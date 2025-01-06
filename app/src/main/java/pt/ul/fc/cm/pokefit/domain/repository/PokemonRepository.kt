package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.utils.Response

interface PokemonRepository {

    suspend fun savePokemon(uid: String, pokemon: Pokemon): Response<Unit>

    suspend fun getPokemonList(limit: Int): List<Pokemon>

    suspend fun getPokemonInfo(name: String): Pokemon

}