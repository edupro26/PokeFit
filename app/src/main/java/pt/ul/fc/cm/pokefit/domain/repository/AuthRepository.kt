package pt.ul.fc.cm.pokefit.domain.repository

import com.google.firebase.auth.FirebaseUser
import pt.ul.fc.cm.pokefit.utils.Response

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUp(email: String, password: String): Response<Unit>

    suspend fun signIn(email: String, password: String): Response<Unit>

    fun signOut()

}