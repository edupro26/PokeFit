package pt.ul.fc.cm.pokefit.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface StepCountRepository {

    val stepsFlow: MutableStateFlow<Long>

    suspend fun updateDailySteps(uid: String)

    suspend fun saveSteps(sensorValue: Long, uid: String)

    suspend fun getTodaySteps(uid: String): Long

}