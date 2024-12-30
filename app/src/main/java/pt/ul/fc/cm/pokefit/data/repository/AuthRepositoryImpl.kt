package pt.ul.fc.cm.pokefit.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser get() = auth.currentUser

    override suspend fun signUp(email: String, password: String): Response<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure("Failed to sign up")
        }
    }

    override suspend fun signIn(email: String, password: String): Response<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure("Failed to sign in. Please check your credentials.")
        }
    }

    override suspend fun continueWithGoogle(idToken: String): Response<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
            Response.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure("Google authentication failed")
        }
    }

    override fun getAuthState() = callbackFlow {
        val initialState = auth.currentUser != null
        trySend(initialState)
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose { auth.removeAuthStateListener(authStateListener) }
    }

    override fun signOut() = auth.signOut()

}