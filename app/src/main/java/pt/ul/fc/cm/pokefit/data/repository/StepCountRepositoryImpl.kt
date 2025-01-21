package pt.ul.fc.cm.pokefit.data.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import pt.ul.fc.cm.pokefit.data.room.dao.StepCountDao
import pt.ul.fc.cm.pokefit.domain.model.StepCount
import pt.ul.fc.cm.pokefit.domain.repository.StepCountRepository
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class StepCountRepositoryImpl @Inject constructor(
    private val stepsDao: StepCountDao,
) : StepCountRepository {

    override val stepsFlow: MutableStateFlow<Long> = MutableStateFlow(0)

    override suspend fun updateDailySteps(uid: String) {
        stepsFlow.emit(getTodaySteps(uid))
    }

    override suspend fun saveSteps(sensorValue: Long, uid: String) {
        val stepCount = StepCount(
            steps = sensorValue,
            createdAt = Instant.now().toString(),
            userId = uid
        )
        Log.d("StepCountRepository", "Storing step count: $stepCount")
        stepsDao.insertAll(stepCount)
        updateDailySteps(uid)
    }

    override suspend fun getTodaySteps(uid: String): Long {
        val todayAtMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)
        val dataPoints = stepsDao.getTodaySteps(todayAtMidnight.toString(), uid)
        return when {
            dataPoints.isEmpty() -> 0
            else -> {
                val todaySteps = dataPoints.last().steps - dataPoints.first().steps
                todaySteps
            }
        }
    }

}