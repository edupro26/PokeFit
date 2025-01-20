package pt.ul.fc.cm.pokefit.domain.repository

interface StepCountRepository {

    suspend fun saveSteps(sensorValue: Long, uid: String)

    suspend fun getTodaySteps(uid: String): Long

}