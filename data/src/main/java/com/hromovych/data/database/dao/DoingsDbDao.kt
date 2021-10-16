package com.hromovych.data.database.dao

import androidx.room.*
import com.hromovych.data.embedded.DailyDoingFull
import com.hromovych.data.entities.DailyDoing
import com.hromovych.data.entities.Doing
import kotlinx.coroutines.flow.Flow

@Dao
interface DoingsDbDao {

    @Query("SELECT * FROM doings")
    fun getDoings(): Flow<List<Doing>>

    @Query("SELECT * FROM doings WHERE active = 1")
    suspend fun getActiveDoings(): List<Doing>

    @Query("SELECT * FROM daily_doings WHERE date = :date")
    suspend fun getDailyDoings(date: Int): List<DailyDoing>

    @Delete
    suspend fun deleteDailyDoing(doing: DailyDoing): Int

    @Update
    suspend fun updateDailyDoing(vararg doing: DailyDoing)

    @Update
    suspend fun updateDoing(doing: Doing)

    @Transaction
    @Query("SELECT * FROM daily_doings WHERE date = :date ORDER BY position")
    fun getDailyDoingsFull(date: Int) : Flow<List<DailyDoingFull>>

    @Insert
    suspend fun addDoing(doing: Doing): Long

    @Insert
    suspend fun addDailyDoing(dailyDoing: DailyDoing): Long

    @Insert
    suspend fun addAllDailyDoing(vararg doings: DailyDoing)

    @Query("SELECT * FROM doings WHERE active = 1 AND id NOT IN (SELECT doingId FROM daily_doings WHERE date = :date) ORDER BY title")
    suspend fun getNewDoingsForDay(date: Int) : List<Doing>
}