package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.data.entities.DailyDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DoingsDbDao {

    @Query("SELECT * FROM doings")
    suspend fun getDoings(): List<Doing>

    @Query("SELECT title from doings where id = :doingId")
    suspend fun getDoingTitle(doingId: Long): String

    @Query("SELECT * FROM daily_doings WHERE date = :date")
    suspend fun getDailyDoings(date: Int): List<DailyDoing>

    @Delete
    suspend fun deleteDailyDoing(doing: DailyDoing): Int

    @Update
    suspend fun updateDailyDoing(doing: DailyDoing)

    @Update
    suspend fun updateDoing(doing: Doing)

    @Transaction
    @Query("SELECT * FROM daily_doings WHERE date = :date")
    fun getDailyDoingsFull(date: Int) : LiveData<List<DailyDoingFull>>

    @Insert
    suspend fun addDoing(doing: Doing): Long
//
//    @Transaction
//    @Insert
//    suspend fun addDailyDoing(dailyDoingFull: DailyDoingFull)
}