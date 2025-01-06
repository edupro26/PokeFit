package pt.ul.fc.cm.pokefit.presentation.screens.auth.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.Authentication
import pt.ul.fc.cm.pokefit.presentation.screens.auth.AuthState
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val authentication: Authentication
) : ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun signIn(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _state.value = AuthState(isLoading = true)
        val response = authentication.signIn(email, password)
        when (response) {
            is Response.Success -> {
                _state.value = _state.value.copy(success = true)
            }
            is Response.Failure -> {
                _state.value = AuthState(error = response.error)
            }
            is Response.Loading -> {}
        }
    }

    fun signInWithGoogle(credential: Credential) = viewModelScope.launch {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            _state.value = AuthState(isLoading = true)
            val response = authentication.continueWithGoogle(googleIdTokenCredential.idToken)
            when (response) {
                is Response.Success -> {
                    _state.value = _state.value.copy(success = true)
                }
                is Response.Failure -> {
                    _state.value = AuthState(error = response.error)
                }
                is Response.Loading -> {}
            }
        } else {
            _state.value = AuthState(error = "Unexpected type of credential")
        }
    }

}