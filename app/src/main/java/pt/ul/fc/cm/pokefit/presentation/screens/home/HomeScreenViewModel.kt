package pt.ul.fc.cm.pokefit.presentation.screens.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.sensor.MeasurableSensor
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val stepCounter: MeasurableSensor
) : ViewModel() {

    private var initialStepCount = 0

    private val _steps = mutableIntStateOf(0)
    val steps: State<Int> = _steps

    fun countSteps() = viewModelScope.launch {
        if (!stepCounter.isListening()) {
            stepCounter.startListening()
            stepCounter.setOnSensorValuesChangedListener { values ->
                if (initialStepCount == 0) {
                    initialStepCount = values[0].toInt()
                }
                _steps.intValue = values[0].toInt() - initialStepCount
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stepCounter.stopListening()
    }
}
