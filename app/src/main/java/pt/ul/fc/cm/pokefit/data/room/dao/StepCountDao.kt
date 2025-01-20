package pt.ul.fc.cm.pokefit.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.ul.fc.cm.pokefit.domain.model.StepCount

@Dao
interface StepCountDao {

    @Insert
    suspend fun insertAll(vararg steps: StepCount)

    @Query("SELECT * FROM steps WHERE user_id = :uid " +
            "AND created_at >= date(:startDateTime) " +
            "AND created_at < date(:startDateTime, '+1 day')")
    suspend fun getTodaySteps(startDateTime: String, uid: String): Array<StepCount>

}