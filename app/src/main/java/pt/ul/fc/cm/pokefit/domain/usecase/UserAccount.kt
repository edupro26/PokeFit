package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.tasks.await
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class UserAccount @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend fun signIn(email: String, password: String): Response<Unit> {
        return authRepository.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String, name: String): Response<Unit> {
        val authResponse = authRepository.signUp(email, password)
        if (authResponse is Response.Success) {
            val user = buildNewUser(name)
            val storeResponse = userRepository.saveUser(user)
            if (storeResponse is Response.Failure) {
                authRepository.currentUser!!.delete().await()
                return storeResponse
            }
        }
        return authResponse
    }

    suspend fun continueWithGoogle(idToken: String): Response<Unit> {
        val authResponse = authRepository.continueWithGoogle(idToken)
        if (authResponse is Response.Success) {
            val uid = authRepository.currentUser!!.uid
            if (userRepository.getUserById(uid) is Response.Failure) {
                val user = buildNewUser()
                val storeResponse = userRepository.saveUser(user)
                if (storeResponse is Response.Failure) {
                    authRepository.currentUser!!.delete().await()
                    return storeResponse
                }
            }
        }
        return authResponse
    }

    fun signOut() = authRepository.signOut()

    fun refreshAuthState() = authRepository.getAuthState()

    fun getCurrentUser() = authRepository.currentUser

    private fun buildNewUser(name: String? = null): User {
        val firebaseUser = authRepository.currentUser!!
        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email!!,
            username = firebaseUser.email!!.substringBefore('@'),
            displayName = name ?: firebaseUser.displayName!!,
            photoUrl = firebaseUser.photoUrl?.toString()
        )
    }
}