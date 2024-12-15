package pt.ul.fc.cm.pokefit.ui.screens.auth.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ul.fc.cm.pokefit.domain.usecase.Authentication
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authentication: Authentication
) : ViewModel() {

    fun isSignedIn(): Boolean = authentication.isSignedIn()

}