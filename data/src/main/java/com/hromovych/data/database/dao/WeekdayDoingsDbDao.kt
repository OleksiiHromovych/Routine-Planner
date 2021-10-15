package com.hromovych.data.database.dao

import androidx.room.*
import com.hromovych.data.embedded.FullWeekdayDoing
import com.hromovych.data.entities.Doing
import com.hromovych.data.entities.WeekdayDoing
import com.hromovych.domain.utils.Weekday
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekdayDoingsDbDao {

    @Transaction
    @Query("SELECT * FROM weekday_doing WHERE weekday = :dayId ORDER BY position")
    fun getWeekdayDoings(dayId: Int): Flow<List<FullWeekdayDoing>>

    @Insert
    suspend fun addWeekdayDoing(weekdayDoing: WeekdayDoing): Long

    @Delete
    suspend fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing)

    @Update
    suspend fun updateWeekdayDoings(weekdayDoings: List<WeekdayDoing>)

    @Query("SELECT * FROM doings WHERE active = 1 AND id NOT IN (SELECT doingId FROM weekday_doing WHERE weekday = :dayId)")
    suspend fun getNewDoingsForDay(dayId: Int): List<Doing>

    suspend fun getNewDoingsForDay(weekday: Weekday) = getNewDoingsForDay(weekday.dayId)

    @Insert
    suspend fun addAllWeekdayDoings(vararg arrayOfWeekdayDoings: WeekdayDoing)
}