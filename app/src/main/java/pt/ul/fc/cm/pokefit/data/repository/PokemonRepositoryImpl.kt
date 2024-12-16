package pt.ul.fc.cm.pokefit.data.repository

import pt.ul.fc.cm.pokefit.data.remote.PokeApi
import pt.ul.fc.cm.pokefit.data.remote.dtos.fromDto
import pt.ul.fc.cm.pokefit.domain.model.Pokemon
import pt.ul.fc.cm.pokefit.domain.model.PokemonDetail
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return pokeApi.getPokemonList(limit).fromDto()
    }

    override suspend fun getPokemonDetails(name: String): PokemonDetail {
        return pokeApi.getPokemonDetails(name).fromDto()
    }

}