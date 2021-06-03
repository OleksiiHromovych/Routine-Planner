package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing
import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeekdayDoingsDbDao {

    @Transaction
    @Query("SELECT * FROM weekday_doing WHERE weekday = :dayId")
    fun getWeekdayDoings(dayId: Int): LiveData<List<FullWeekdayDoing>>

    @Insert
    suspend fun addWeekdayDoing(weekdayDoing: WeekdayDoing): Long

    @Delete
    suspend fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing)

    @Update
    suspend fun updateWeekdayDoing(weekdayDoing: WeekdayDoing)

    @Query("SELECT * FROM doings WHERE active = 1 AND id NOT IN (SELECT doingId FROM weekday_doing WHERE weekday = :dayId)")
    suspend fun getNewDoingsForDay(dayId: Int): List<Doing>

    suspend fun getNewDoingsForDay(weekday: Weekday) = getNewDoingsForDay(weekday.dayId)

    @Insert
    suspend fun addAllWeekdayDoings(vararg arrayOfWeekdayDoings: WeekdayDoing)
}