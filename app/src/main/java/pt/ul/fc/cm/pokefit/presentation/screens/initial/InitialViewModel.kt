package pt.ul.fc.cm.pokefit.presentation.screens.initial

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.Authentication
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val authentication: Authentication
) : ViewModel() {

    private val _authState = mutableStateOf(AuthState(isLoading = true))
    val authState: State<AuthState> = _authState

    init {
        getAuthState()
    }

    fun getAuthState() = viewModelScope.launch {
        delay(500) // Wait for all app resources to load
        authentication.refreshAuthState()
            .collect { isSignedIn  ->
                _authState.value = AuthState(
                    isLoading = false,
                    isSignedIn = isSignedIn
                )
        }
    }

}