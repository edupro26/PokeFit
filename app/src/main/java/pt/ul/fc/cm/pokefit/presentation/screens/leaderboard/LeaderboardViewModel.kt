package pt.ul.fc.cm.pokefit.presentation.screens.leaderboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.ul.fc.cm.pokefit.domain.usecase.Leaderboard
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.utils.Constants.TOP_10
import pt.ul.fc.cm.pokefit.utils.Resource
import javax.inject.Inject


@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    userAccount: UserAccount,
    private val leaderboard: Leaderboard
) : ViewModel() {

    private val _state = mutableStateOf(LeaderboardListState())
    val state: State<LeaderboardListState> = _state

    private val limit = TOP_10

    val uid = userAccount.getCurrentUser()!!.uid

    init {
        loadLeaderboard()
    }

    private fun loadLeaderboard() {
        leaderboard.loadLeaderBoard(limit).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = LeaderboardListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = LeaderboardListState(users = resource.data)
                }
                is Resource.Failure -> {
                    _state.value = LeaderboardListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}
