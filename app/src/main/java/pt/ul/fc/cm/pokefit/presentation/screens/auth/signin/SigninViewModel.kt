package pt.ul.fc.cm.pokefit.presentation.screens.auth.signin

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
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

    fun signInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                _state.value = authentication.continueWithGoogle(googleIdTokenCredential.idToken)
            } else {
                _state.value = Response.Failure("Unexpected type of credential")
            }
        }
    }

}