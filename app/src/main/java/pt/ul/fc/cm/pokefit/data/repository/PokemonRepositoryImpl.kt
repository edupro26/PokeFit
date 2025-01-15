package pt.ul.fc.cm.pokefit.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction.DESCENDING
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
            store.runTransaction { transaction ->
                val userRef = store.collection("users").document(uid)
                val pokemonRef = userRef.collection("pokemon")
                    .document(pokemon.id.toString())
                transaction.set(pokemonRef, pokemon)
                transaction.update(userRef, "pokemonCount", FieldValue.increment(1))
            }
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to save pokemon (${e::class.java.simpleName})")
            Response.Failure("Failed to save pokemon")
        }
    }

    override suspend fun getUserPokemon(uid: String): Response<List<Pokemon>> {
        return try {
            val pokemon = store.collection("users")
                .document(uid).collection("pokemon")
                .orderBy("selected", DESCENDING)
                .get().await()
                .toObjects(Pokemon::class.java)
            Response.Success(pokemon)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to get all pokemon (${e::class.java.simpleName})")
            Response.Failure("Failed to get all pokemon")
        }
    }

    override suspend fun getPokemonListApi(limit: Int): List<Pokemon> {
        return pokeApi.getPokemonList(limit).fromDto()
    }

    override suspend fun getPokemonInfoApi(name: String): Pokemon {
        return pokeApi.getPokemonInfo(name).fromDto()
    }

}