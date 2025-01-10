package pt.ul.fc.cm.pokefit.presentation.screens.leaderboard

import pt.ul.fc.cm.pokefit.domain.model.User

data class LeaderboardListState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)