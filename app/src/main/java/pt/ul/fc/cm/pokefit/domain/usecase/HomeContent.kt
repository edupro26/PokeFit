package pt.ul.fc.cm.pokefit.domain.usecase

import pt.ul.fc.cm.pokefit.domain.repository.StepCountRepository
import javax.inject.Inject

class HomeContent @Inject constructor(
    private val stepCountRepository: StepCountRepository
) {

    suspend fun collectSteps(uid: String, onStepsCollected: (Long) -> Unit) {
        stepCountRepository.updateDailySteps(uid)
        stepCountRepository.stepsFlow.collect { steps ->
            onStepsCollected(steps)
        }
    }

}