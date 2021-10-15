package com.hromovych.domain.repository.weekday_doings

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.utils.Weekday
import kotlinx.coroutines.flow.Flow

interface WeekdayDoingsRepository {

    fun getWeekdayDoings(weekday: Weekday): Flow<List<WeekdayDoing>>

    suspend fun addWeekdayDoings(vararg doings: WeekdayDoing)

    suspend fun updateWeekdayDoings(doings: List<WeekdayDoing>)

    suspend fun deleteWeekdayDoing(doing: WeekdayDoing)

}