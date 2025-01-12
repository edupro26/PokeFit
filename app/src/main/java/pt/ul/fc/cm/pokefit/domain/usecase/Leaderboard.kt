package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class Leaderboard @Inject constructor(
    private val userRepository: UserRepository
) {

    fun loadLeaderBoard(limit: Int): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        when (val response = userRepository.getGlobalLeaderboard(limit)) {
            is Response.Success -> {
                emit(Resource.Success(response.data!!))
            }
            is Response.Failure -> {
                emit(Resource.Failure(response.error))
            }
        }
    }

}