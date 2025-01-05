package pt.ul.fc.cm.pokefit.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.utils.Constants.POKEMON_COUNT
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonList @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> = flow {
        try {
            emit(pokemonRepository.getPokemonList(POKEMON_COUNT))
        } catch (e: HttpException) {
            Log.e("GetPokemonList", "HttpException: ${e.message()}")
        } catch (e: IOException) {
            Log.e("GetPokemonList", "IOException: ${e.message}")
        }
    }
}