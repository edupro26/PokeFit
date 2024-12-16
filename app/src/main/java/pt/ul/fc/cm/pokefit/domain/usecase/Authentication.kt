package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class Authentication @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend fun signUp(email: String, password: String, name: String): Response<Unit> {
        val signupResponse = authRepository.signUp(email, password)
        if (signupResponse is Response.Success) {
            val newUser = User(
                uid = authRepository.currentUser!!.uid,
                email = email,
                username = email.substringBefore('@'),
                displayName = name
            )
            val storeResponse = userRepository.saveUser(newUser)
            if (storeResponse is Response.Failure) {
                authRepository.currentUser!!.delete().await()
                return storeResponse
            }
        }
        return signupResponse
    }

    suspend fun signUpWithGoogle(idToken: String): Response<Unit> {
        val googleResponse = authRepository.continueWithGoogle(idToken)
        if (googleResponse is Response.Success) {
            val user = authRepository.currentUser!!
            val newUser = User(
                uid = user.uid,
                email = user.email!!,
                username = user.email!!.substringBefore('@'),
                displayName = user.displayName!!,
                photoUrl = user.photoUrl.toString()
            )
            val storeResponse = userRepository.saveUser(newUser)
            if (storeResponse is Response.Failure) {
                authRepository.currentUser!!.delete().await()
                return storeResponse
            }
        }
        return googleResponse
    }

    suspend fun signIn(email: String, password: String): Response<Unit> {
        return authRepository.signIn(email, password)
    }

    suspend fun signInWithGoogle(idToken: String): Response<Unit> {
        return authRepository.continueWithGoogle(idToken)
    }

    fun isSignedIn(): Boolean = authRepository.currentUser != null

    fun signOut() = authRepository.signOut()

    fun getCurrentUser() = authRepository.currentUser
}