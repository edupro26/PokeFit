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
            locked = false,
            selected = true
        )
        return pokemonRepository.savePokemon(uid, starterPokemon)
    }

    suspend fun getUserPokemonCount(uid: String): Response<Int> {
        return userRepository.getUserById(uid).let {
            when (it) {
                is Response.Success -> Response.Success(it.data?.pokemonCount!!)
                is Response.Failure -> Response.Failure(it.error)
            }
        }
    }

    fun loadPokemonList(uid: String): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading)
        when (val locked = pokemonRepository.getAllUserPokemon(uid)) {
            is Response.Success -> {
                try {
                    val unlocked = pokemonRepository.getPokemonListApi(POKEMON_COUNT)
                    val pokemon = (locked.data!! + unlocked).distinctBy { it.id }
                    emit(Resource.Success(pokemon))
                } catch (e: HttpException) {
                    emit(Resource.Failure(e.localizedMessage ?: "An unexpected error occurred"))
                } catch (_: IOException) {
                    emit(Resource.Failure("Couldn't reach server. Check your internet connection."))
                }
            }
            is Response.Failure -> {
                emit(Resource.Failure(locked.error))
            }
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

}