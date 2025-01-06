package pt.ul.fc.cm.pokefit.presentation.screens.auth

data class AuthState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)