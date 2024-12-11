package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.PokemonDetail
import pt.ul.fc.cm.pokefit.domain.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int): List<Pokemon>

    suspend fun getPokemonDetails(name: String): PokemonDetail

}