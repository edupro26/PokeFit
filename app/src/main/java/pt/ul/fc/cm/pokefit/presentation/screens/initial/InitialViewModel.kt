package pt.ul.fc.cm.pokefit.presentation.screens.initial

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val userAccount: UserAccount
) : ViewModel() {

    private val _state = mutableStateOf(InitialState(isLoading = true))
    val state: State<InitialState> = _state

    init {
        getAuthState()
    }

    fun getAuthState() = viewModelScope.launch {
        delay(500) // Wait for all app resources to load
        userAccount.refreshAuthState()
            .collect { isSignedIn  ->
                _state.value = InitialState(
                    isLoading = false,
                    isSignedIn = isSignedIn
                )
        }
    }

}