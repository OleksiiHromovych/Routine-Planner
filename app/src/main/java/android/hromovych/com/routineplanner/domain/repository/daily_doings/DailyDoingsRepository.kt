package android.hromovych.com.routineplanner.domain.repository.daily_doings

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import androidx.lifecycle.LiveData

interface DailyDoingsRepository {

    fun getDailyDoings(date: Int): LiveData<List<DailyDoing>>

    suspend fun updateDailyDoing(dailyDoing: DailyDoing)

    suspend fun addDailyDoings(vararg dailyDoings: DailyDoing)

    suspend fun deleteDailyDoing(dailyDoing: DailyDoing)

    suspend fun getNewDoingsForDay(date: Int): List<Doing>
}