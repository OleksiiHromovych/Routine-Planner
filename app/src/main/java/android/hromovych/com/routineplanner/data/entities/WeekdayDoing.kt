package android.hromovych.com.routineplanner.data.entities

import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.room.Entity

@Entity(tableName = "weekday_doing")
data class WeekdayDoing(
    var weekDay: Weekday,
    var doingId: Long,
    var position: Int
)