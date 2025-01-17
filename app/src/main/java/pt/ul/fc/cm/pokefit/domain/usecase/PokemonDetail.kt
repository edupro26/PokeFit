package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class PokemonDetail @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend fun selectPokemon(id: Int, uid: String): Response<Unit> {
        return pokemonRepository.selectPokemon(id, uid)
    }

    suspend fun unlockPokemon(pokemon: Pokemon, amount: Int, uid: String): Response<Unit> {
        val unlocked = pokemon.copy(locked = false)
        return pokemonRepository.unlockPokemon(unlocked, amount, uid)
    }

    fun loadPokemon(id: Int, uid: String): Flow<Resource<Pokemon>> = flow {
        emit(Resource.Loading)
        when (val response = pokemonRepository.getUserPokemon(id, uid)) {
            is Response.Success -> {
                if (response.data != null) {
                    emit(Resource.Success(response.data))
                } else {
                    emit(Resource.Success(
                        pokemonRepository.getPokemonInfoApi(id)
                    ))
                }
            }
            is Response.Failure -> emit(Resource.Failure(response.error))
        }
    }

}