package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.utils.Response

interface UserRepository {

    suspend fun saveUser(user: User): Response<Unit>

    suspend fun incrementPokemonCount(uid: String): Response<Unit>

    suspend fun getUserById(uid: String): Response<User>

}