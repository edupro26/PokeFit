package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Constants.POKEMON_COUNT
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
            isLocked = false,
            isSelected = true
        )
        var response = pokemonRepository.savePokemon(uid, starterPokemon)
        if (response is Response.Success) {
            response = userRepository.incrementPokemonCount(uid)
            return response
        }
        return response
    }

    fun loadPokemonList(): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading)
            val list = pokemonRepository.getPokemonList(POKEMON_COUNT)
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
            val p1 = pokemonRepository.getPokemonInfo(STARTER_1)
            val p2 = pokemonRepository.getPokemonInfo(STARTER_2)
            val p3 = pokemonRepository.getPokemonInfo(STARTER_3)
            emit(Resource.Success(listOf(p1, p2, p3)))
        } catch (e: HttpException) {
            emit(Resource.Failure(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (_: IOException) {
            emit(Resource.Failure("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getUserPokemonCount(uid: String): Flow<Resource<Int>> = flow {
        emit(Resource.Loading)
        val user = userRepository.getUserById(uid)
        when (user) {
            is Response.Success -> {
                val count = user.data?.pokemonCount!!
                emit(Resource.Success(count))
            }
            is Response.Failure -> {
                emit(Resource.Failure(user.error))
            }
        }
    }

}