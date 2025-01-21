package pt.ul.fc.cm.pokefit.presentation.screens.home.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.HomeContent
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userAccount: UserAccount,
    private val homeContent: HomeContent
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val uid = userAccount.getCurrentUser()!!.uid

    init {
        observeDailySteps()
    }

    private fun observeDailySteps() = viewModelScope.launch {
        homeContent.collectSteps(uid) { steps ->
            _state.value = _state.value.copy(
                steps = steps.toInt(),
                calories = (steps * 0.04).toInt(),
                distance = "%.2f".format(
                    Locale.ENGLISH,
                    steps * 0.000762
                ).toDouble(),
                activeMinutes = (steps / 80).toInt()
            )
        }
    }
}

