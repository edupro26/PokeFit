package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int): List<Pokemon>

    suspend fun getPokemonInfo(name: String): Pokemon

}