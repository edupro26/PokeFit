package pt.ul.fc.cm.pokefit.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction.DESCENDING
import com.google.firebase.firestore.Transaction
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

    private val listCache = mutableListOf<Pokemon>()
    private val mapCache = mutableMapOf<Int, Pokemon>()

    override suspend fun savePokemon(uid: String, pokemon: Pokemon): Response<Unit> {
        return try {
            store.runTransaction { transaction ->
                transaction.performSavePokemon(pokemon, uid)
            }.await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to save pokemon (${e::class.java.simpleName})")
            Response.Failure("Failed to save pokemon")
        }
    }

    override suspend fun unlockPokemon(pokemon: Pokemon, amount: Int, uid: String): Response<Unit> {
        return try {
            val userRef = store.collection("users").document(uid)
            val fitCoins = userRef.get().await().getLong("fitCoins")!!
            if (fitCoins < amount) {
                return Response.Failure("Insufficient Fit Coins")
            }
            store.runTransaction { transaction ->
                transaction.update(userRef, "fitCoins", fitCoins - amount)
                transaction.performSavePokemon(pokemon, uid)
            }.await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to unlock pokemon (${e::class.java.simpleName})")
            Response.Failure("Failed to unlock pokemon")
        }
    }

    private fun Transaction.performSavePokemon(pokemon: Pokemon, uid: String) {
        val userRef = store.collection("users").document(uid)
        val pokemonRef = userRef.collection("pokemon")
            .document(pokemon.id.toString())
        this.set(pokemonRef, pokemon)
        this.update(userRef, "pokemonCount", FieldValue.increment(1))
    }

    override suspend fun selectPokemon(id: Int, uid: String): Response<Unit> {
        return try {
            val collectionRef = store.collection("users")
                .document(uid).collection("pokemon")
            val selectedQuery = collectionRef.whereEqualTo("selected", true)
                .get().await()
            store.runTransaction { transaction ->
                for (doc in selectedQuery.documents) {
                    transaction.update(doc.reference, "selected", false)
                }
                val pokemonRef = collectionRef.document(id.toString())
                transaction.update(pokemonRef, "selected", true)
            }.await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to select pokemon $id (${e::class.java.simpleName})")
            Response.Failure("Failed to select pokemon")
        }
    }

    override suspend fun getAllUserPokemon(uid: String): Response<List<Pokemon>> {
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

    override suspend fun getUserPokemon(id: Int, uid: String): Response<Pokemon> {
        return try {
            val pokemon = store.collection("users")
                .document(uid).collection("pokemon")
                .document(id.toString())
                .get().await()
            if (pokemon.exists()) {
                Response.Success(pokemon.toObject(Pokemon::class.java))
            } else {
                Response.Success(null)
            }
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Failed to get pokemon $id (${e::class.java.simpleName})")
            Response.Failure("Failed to get pokemon")
        }
    }

    override suspend fun getPokemonListApi(limit: Int): List<Pokemon> {
        if (listCache.isNotEmpty()) return listCache
        return pokeApi.getPokemonList(limit)
            .fromDto()
            .also { listCache.addAll(it) }
    }

    override suspend fun getPokemonInfoApi(id: Int): Pokemon {
        if (mapCache.containsKey(id)) return mapCache[id]!!
        return pokeApi.getPokemonInfo(id)
            .fromDto()
            .also { mapCache[id] = it }
    }

}