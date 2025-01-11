package pt.ul.fc.cm.pokefit.presentation.screens.leaderboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.utils.Response


@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = mutableStateOf(LeaderboardListState())
    val state: State<LeaderboardListState> = _state

    val currentUserId: String? = authRepository.currentUser?.uid

    init {
        loadLeaderboard()
    }

    private fun loadLeaderboard(limit: Int = 10) = viewModelScope.launch {
        _state.value = LeaderboardListState(isLoading = true)

        val response = userRepository.getGlobalLeaderboard(limit)
        when (response) {
            is Response.Success -> {
                val users = response.data ?: emptyList()
                _state.value = LeaderboardListState(
                    isLoading = false,
                    users = users
                )
            }
            is Response.Failure -> {
                _state.value = LeaderboardListState(
                    isLoading = false,
                    error = response.error
                )
            }
        }
    }
}
