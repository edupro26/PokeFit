package pt.ul.fc.cm.pokefit.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

class GetUserProfile @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(uid: String): Flow<Response<User>> = flow {
        emit(userRepository.getUserById(uid))
    }

}