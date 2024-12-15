package pt.ul.fc.cm.pokefit.ui.screens.auth.signin

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.Authentication
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val authentication: Authentication
) : ViewModel() {

    private val _state = mutableStateOf<Response<Unit>>(Response.Loading)
    val state: State<Response<Unit>> get() = _state

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = authentication.signIn(email, password)
        }
    }

}