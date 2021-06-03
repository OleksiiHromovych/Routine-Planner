package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.lifecycle.LiveData

interface WeekdayDoingsRepository {

    fun getWeekdayDoings(weekday: Weekday): LiveData<List<WeekdayDoing>>

    suspend fun addWeekdayDoings(vararg doings: WeekdayDoing)

    suspend fun updateWeekdayDoing(doing: WeekdayDoing)

    suspend fun deleteWeekdayDoing(doing: WeekdayDoing)

}