package android.hromovych.com.routineplanner.domain.repository.daily_doings

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import kotlinx.coroutines.flow.Flow

interface DailyDoingsRepository {

    fun getDailyDoings(date: Int): Flow<List<DailyDoing>>

    suspend fun updateDailyDoing(dailyDoing: DailyDoing)

    suspend fun addDailyDoings(vararg dailyDoings: DailyDoing)

    suspend fun deleteDailyDoing(dailyDoing: DailyDoing)

    suspend fun getNewDoingsForDay(date: Int): List<Doing>
}