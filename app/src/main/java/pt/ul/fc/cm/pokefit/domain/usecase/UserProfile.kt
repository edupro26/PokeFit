package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import pt.ul.fc.cm.pokefit.utils.Resource
import javax.inject.Inject

class UserProfile @Inject constructor(
    private val userRepository: UserRepository
) {

    fun load(uid: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        val response = userRepository.getUserById(uid)
        when (response) {
            is Response.Success -> emit(Resource.Success(response.data))
            is Response.Failure -> emit(Resource.Failure(response.error))
            is Response.Loading -> {}
        }
    }

}