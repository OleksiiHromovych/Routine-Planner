package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.data.entities.DailyDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DoingsDbDao {

    @Query("SELECT * FROM doings")
    fun getDoings(): LiveData<List<Doing>>

    @Query("SELECT * FROM doings WHERE active = 1")
    suspend fun getActiveDoings(): List<Doing>

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

    @Insert
    suspend fun addDailyDoing(dailyDoing: DailyDoing): Long

    @Insert
    suspend fun addAllDailyDoing(vararg doings: DailyDoing)

    @Query("SELECT * FROM doings WHERE active = 1 AND id NOT IN (SELECT doingId FROM daily_doings WHERE date = :date)")
    suspend fun getNewDoingsForDay(date: Int) : List<Doing>
}