package pt.ul.fc.cm.pokefit.presentation.screens.home.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.repository.StepCountRepository
import pt.ul.fc.cm.pokefit.domain.sensor.MeasurableSensor
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userAccount: UserAccount,
    private val stepCounter: MeasurableSensor,
    private val stepsRepository: StepCountRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val uid = userAccount.getCurrentUser()!!.uid

    private var lastRecordedSteps: Long = 0

    init {
        loadMetrics()
    }

    fun countSteps() {
        if (!stepCounter.isListening()) {
            stepCounter.startListening()
            stepCounter.setOnSensorValuesChangedListener { values ->
                handleStepUpdates(values)
            }
        }
    }

    private fun handleStepUpdates(values: List<Float>) {
        val steps = values.firstOrNull()?.toLong() ?: 0
        viewModelScope.launch {
            if (steps > lastRecordedSteps) {
                stepsRepository.saveSteps(steps, uid)
                lastRecordedSteps = steps
                loadMetrics()
            }
        }
    }

    private fun loadMetrics() = viewModelScope.launch {
        val stepCount = stepsRepository.getTodaySteps(uid)
        _state.value = _state.value.copy(
            steps = stepCount.toInt(),
            calories = (stepCount * 0.04).toInt(),
            distance = "%.2f".format(
                Locale.ENGLISH,
                stepCount * 0.000762
            ).toDouble(),
            activeMinutes = (stepCount / 80).toInt()
        )
    }

    override fun onCleared() {
        super.onCleared()
        stepCounter.stopListening()
    }
}

