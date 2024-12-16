package pt.ul.fc.cm.pokefit.ui.screens.auth.signup

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
class SignupViewModel @Inject constructor(
    private val authentication: Authentication
) : ViewModel() {

    private val _state = mutableStateOf<Response<Unit>>(Response.Loading)
    val state: State<Response<Unit>> get() = _state

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _state.value = authentication.signUp(email, password, name)
        }
    }

}