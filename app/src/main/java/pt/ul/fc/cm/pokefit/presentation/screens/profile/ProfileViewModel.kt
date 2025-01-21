package pt.ul.fc.cm.pokefit.presentation.screens.profile

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.ul.fc.cm.pokefit.domain.service.TrackingService
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.domain.usecase.UserProfile
import pt.ul.fc.cm.pokefit.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userAccount: UserAccount,
    private val userProfile: UserProfile
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _isUserSignedIn = mutableStateOf(false)
    val isUserSignedIn: State<Boolean> = _isUserSignedIn

    init {
        _isUserSignedIn.value = true
        loadUserProfile(userAccount.getCurrentUser()!!.uid)
    }

    fun signOut(context: Context) {
        userAccount.signOut()
        _isUserSignedIn.value = false

        Intent(context, TrackingService::class.java).also {
            it.action = TrackingService.Action.STOP.toString()
            context.startService(it)
        }
    }

    private fun loadUserProfile(uid: String) {
        userProfile.load(uid).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ProfileState(user = resource.data)
                }
                is Resource.Failure -> {
                    _state.value = ProfileState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

}