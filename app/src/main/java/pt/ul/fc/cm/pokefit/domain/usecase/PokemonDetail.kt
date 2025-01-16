package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import javax.inject.Inject
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response

class PokemonDetail @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    fun loadPokemon(id: Int, uid: String): Flow<Resource<Pokemon>> = flow {
        emit(Resource.Loading)
        when (val response = pokemonRepository.getPokemon(id, uid)) {
            is Response.Success -> {
                if (response.data != null) {
                    emit(Resource.Success(response.data))
                } else {
                    emit(Resource.Success(
                        pokemonRepository.getPokemonInfoApi(id.toString())
                    ))
                }
            }
            is Response.Failure -> emit(Resource.Failure(response.error))
        }
    }

}