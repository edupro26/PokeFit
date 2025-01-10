package pt.ul.fc.cm.pokefit.presentation.screens.leaderboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ul.fc.cm.pokefit.domain.repository.UserRepository
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.utils.Response


@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = mutableStateOf(LeaderboardListState())
    val state: State<LeaderboardListState> = _state

    init {
        loadLeaderboard()
    }

    private fun loadLeaderboard(limit: Int = 10) = viewModelScope.launch {
        _state.value = LeaderboardListState(isLoading = true) // Set loading state

        val response = userRepository.getGlobalLeaderboard(limit) // Call the repository method
        when (response) {
            is Response.Success -> {
                val users = response.data ?: emptyList() // Handle nullable data
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
