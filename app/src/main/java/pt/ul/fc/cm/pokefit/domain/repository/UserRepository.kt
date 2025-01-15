package pt.ul.fc.cm.pokefit.domain.repository

import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.utils.Response

interface UserRepository {

    suspend fun saveUser(user: User): Response<Unit>

    suspend fun setScore(uid: String, score: Int): Response<Unit>

    suspend fun getGlobalLeaderboard(limit: Int): Response<List<User>>

    suspend fun getUserById(uid: String): Response<User>
}