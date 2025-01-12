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
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.presentation.screens.auth.AuthState
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val userAccount: UserAccount
) : ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun signIn(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _state.value = AuthState(isLoading = true)
        when (val response = userAccount.signIn(email, password)) {
            is Response.Success -> {
                _state.value = _state.value.copy(success = true)
            }
            is Response.Failure -> {
                _state.value = AuthState(error = response.error)
            }
        }
    }

    fun signInWithGoogle(credential: Credential) = viewModelScope.launch {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            _state.value = AuthState(isLoading = true)
            when (val response = userAccount.continueWithGoogle(googleIdTokenCredential.idToken)) {
                is Response.Success -> {
                    _state.value = _state.value.copy(success = true)
                }
                is Response.Failure -> {
                    _state.value = AuthState(error = response.error)
                }
            }
        } else {
            _state.value = AuthState(error = "Unexpected type of credential")
        }
    }

}