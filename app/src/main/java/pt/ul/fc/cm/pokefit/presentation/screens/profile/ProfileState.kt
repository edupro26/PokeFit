package pt.ul.fc.cm.pokefit.presentation.screens.profile

import pt.ul.fc.cm.pokefit.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
