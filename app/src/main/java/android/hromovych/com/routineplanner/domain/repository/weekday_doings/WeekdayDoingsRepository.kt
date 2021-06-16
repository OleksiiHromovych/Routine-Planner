package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.utils.Weekday
import kotlinx.coroutines.flow.Flow

interface WeekdayDoingsRepository {

    fun getWeekdayDoings(weekday: Weekday): Flow<List<WeekdayDoing>>

    suspend fun addWeekdayDoings(vararg doings: WeekdayDoing)

    suspend fun updateWeekdayDoings(doings: List<WeekdayDoing>)

    suspend fun deleteWeekdayDoing(doing: WeekdayDoing)

}