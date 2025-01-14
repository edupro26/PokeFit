package pt.ul.fc.cm.pokefit.presentation.screens.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private var startTimeMillis: Long? = null // Tempo inicial em milissegundos

    private val _steps = mutableIntStateOf(0)
    val steps: State<Int> = _steps

    private val _calories = mutableIntStateOf(0)
    val calories: State<Int> = _calories

    private val _activeTimeMinutes = mutableIntStateOf(0)
    val activeTimeMinutes: State<Int> = _activeTimeMinutes

    private val caloriesPerStep = 0.04 // Calorias por passo (valor médio)

    fun countSteps() = viewModelScope.launch {
        if (!stepCounter.isListening()) {
            stepCounter.startListening()

            // Armazena o momento inicial
            startTimeMillis = System.currentTimeMillis()

            stepCounter.setOnSensorValuesChangedListener { values ->
                if (initialStepCount == 0) {
                    initialStepCount = values[0].toInt()
                }

                val currentSteps = values[0].toInt() - initialStepCount
                _steps.intValue = currentSteps
                _calories.intValue = (currentSteps * caloriesPerStep).toInt()

                // Atualiza o tempo ativo em minutos
                startTimeMillis?.let { start ->
                    val elapsedMillis = System.currentTimeMillis() - start
                    _activeTimeMinutes.intValue = (elapsedMillis / 60000).toInt()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stepCounter.stopListening()
    }
}

