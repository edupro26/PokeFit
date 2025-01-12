package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Constants.STARTER_1
import pt.ul.fc.cm.pokefit.utils.Constants.STARTER_2
import pt.ul.fc.cm.pokefit.utils.Constants.STARTER_3
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonList @Inject constructor(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository
) {

    suspend fun saveStarterPokemon(uid: String, pokemon: Pokemon): Response<Unit> {
        val starterPokemon = pokemon.copy(
            locked = false,
            selected = true
        )
        var response = pokemonRepository.savePokemon(uid, starterPokemon)
        if (response is Response.Success) {
            response = userRepository.incrementPokemonCount(uid)
            return response
        }
        return response
    }

    fun loadUserPokemon(uid: String): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading)
        when (val response = pokemonRepository.getUserPokemon(uid)) {
            is Response.Success -> {
                emit(Resource.Success(response.data!!))
            }
            is Response.Failure -> {
                emit(Resource.Failure(response.error))
            }
        }
    }

    fun loadLockedPokemon(limit: Int): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading)
            val list = pokemonRepository.getPokemonListApi(limit)
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(Resource.Failure(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (_: IOException) {
            emit(Resource.Failure("Couldn't reach server. Check your internet connection."))
        }
    }

    fun loadStarterPokemon(): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading)
            val p1 = pokemonRepository.getPokemonInfoApi(STARTER_1)
            val p2 = pokemonRepository.getPokemonInfoApi(STARTER_2)
            val p3 = pokemonRepository.getPokemonInfoApi(STARTER_3)
            emit(Resource.Success(listOf(p1, p2, p3)))
        } catch (e: HttpException) {
            emit(Resource.Failure(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (_: IOException) {
            emit(Resource.Failure("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getUserPokemonCount(uid: String): Flow<Resource<Int>> = flow {
        emit(Resource.Loading)
        when (val user = userRepository.getUserById(uid)) {
            is Response.Success -> {
                emit(Resource.Success(user.data?.pokemonCount!!))
            }
            is Response.Failure -> {
                emit(Resource.Failure(user.error))
            }
        }
    }

}