package pt.ul.fc.cm.pokefit.presentation.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.ul.fc.cm.pokefit.domain.usecase.Authentication
import pt.ul.fc.cm.pokefit.domain.usecase.GetUserProfile
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authentication: Authentication,
    private val getUserProfile: GetUserProfile
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _isUserSignedIn = mutableStateOf(false)
    val isUserSignedIn: State<Boolean> = _isUserSignedIn

    init {
        _isUserSignedIn.value = true
        getProfileInfo(authentication.getCurrentUser()!!.uid)
    }

    fun signOut() {
        authentication.signOut()
        _isUserSignedIn.value = false
    }

    private fun getProfileInfo(uid: String) {
        getUserProfile(uid).onEach { response ->
            when (response) {
                is Response.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
                is Response.Success -> {
                    _state.value = state.value.copy(user = response.data)
                }
                is Response.Failure -> {
                    _state.value = state.value.copy(error = response.error)
                }
            }
        }.launchIn(viewModelScope)
    }

}