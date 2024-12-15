package pt.ul.fc.cm.pokefit.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
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
            e.printStackTrace()
            Response.Failure("Failed to save user")
        }
    }

    override suspend fun getCurrentUser(): User? {
        return auth.currentUser?.let { currentUser ->
            val document = store.collection("users")
                .document(currentUser.uid)
                .get()
                .await()
            document.toObject(User::class.java)
        }
    }

}