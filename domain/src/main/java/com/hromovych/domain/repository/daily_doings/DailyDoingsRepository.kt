package com.hromovych.domain.repository.daily_doings

import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.entity.Doing
import kotlinx.coroutines.flow.Flow

interface DailyDoingsRepository {

    fun getDailyDoings(date: Int): Flow<List<DailyDoing>>

    suspend fun updateDailyDoings(vararg dailyDoing: DailyDoing)

    suspend fun addDailyDoings(vararg dailyDoings: DailyDoing)

    suspend fun deleteDailyDoing(dailyDoing: DailyDoing)

    suspend fun getNewDoingsForDay(date: Int): List<Doing>
}