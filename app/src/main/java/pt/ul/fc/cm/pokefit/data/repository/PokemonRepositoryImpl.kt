package pt.ul.fc.cm.pokefit.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.data.remote.PokeApi
import pt.ul.fc.cm.pokefit.data.remote.dtos.fromDto
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.repository.PokemonRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi,
    private val store: FirebaseFirestore
) : PokemonRepository {

    override suspend fun savePokemon(uid: String, pokemon: Pokemon): Response<Unit> {
        return try {
            store.collection("users").document(uid)
                .collection("pokemon").document(pokemon.id.toString())
                .set(pokemon)
                .await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to save pokemon (${e::class.java.simpleName})")
            Response.Failure("Failed to save pokemon")
        }
    }

    override suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return pokeApi.getPokemonList(limit).fromDto()
    }

    override suspend fun getPokemonInfo(name: String): Pokemon {
        return pokeApi.getPokemonInfo(name).fromDto()
    }

}