package pt.ul.fc.cm.pokefit.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import pt.ul.fc.cm.pokefit.utils.Response

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUp(email: String, password: String): Response<Unit>

    suspend fun signIn(email: String, password: String): Response<Unit>

    suspend fun continueWithGoogle(idToken: String): Response<Unit>

    fun getAuthState(): Flow<Boolean>

    fun signOut()

}