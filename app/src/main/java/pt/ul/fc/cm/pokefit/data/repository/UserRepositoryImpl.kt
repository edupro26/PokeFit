package pt.ul.fc.cm.pokefit.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction.DESCENDING
import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val store: FirebaseFirestore
) : UserRepository {

    override suspend fun saveUser(user: User): Response<Unit> {
        return try {
            store.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to save user (${e::class.java.simpleName})")
            Response.Failure("Failed to save user")
        }
    }

    override suspend fun setScore(uid: String, score: Int): Response<Unit> {
        // TODO: Finish the user score logic (and rename to FitPoints)
        return try {
            store.collection("users").document(uid)
                .update("userScore", score)
                .await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to set user score (${e::class.java.simpleName}): ${e.message}")
            Response.Failure("Failed to set user score")
        }
    }

    override suspend fun getGlobalLeaderboard(limit: Int): Response<List<User>> {
        return try {
            val users = store.collection("users")
                .orderBy("userScore", DESCENDING)
                .limit(limit.toLong())
                .get().await()
                .toObjects(User::class.java)
            Response.Success(users)
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to retrieve global leaderboard (${e::class.java.simpleName}): ${e.message}")
            Response.Failure("Failed to retrieve global leaderboard")
        }
    }

    override suspend fun getUserById(uid: String): Response<User> {
        return try {
            Response.Success(
                store.collection("users")
                    .document(uid)
                    .get()
                    .await()
                    .toObject(User::class.java)!!
            )
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to get user (${e::class.java.simpleName})")
            Response.Failure("Failed to get user data")
        }
    }

}