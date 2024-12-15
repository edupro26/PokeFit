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

    suspend fun signIn(email: String, password: String): Response<Unit> {
        return authRepository.signIn(email, password)
    }

    suspend fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }

    fun isSignedIn(): Boolean = authRepository.currentUser != null
}