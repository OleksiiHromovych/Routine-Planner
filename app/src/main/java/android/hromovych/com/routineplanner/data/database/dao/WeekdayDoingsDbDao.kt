package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeekdayDoingsDbDao {

    @Transaction
    @Query("SELECT * FROM weekday_doing WHERE _id = :dayId")
    fun getWeekdayDoings(dayId: Int): LiveData<List<FullWeekdayDoing>>

    @Insert
    suspend fun addWeekdayDoing(weekdayDoing: WeekdayDoing): Long

    @Delete
    suspend fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing)

    @Update
    suspend fun updateWeekdayDoing(weekdayDoing: WeekdayDoing)
}