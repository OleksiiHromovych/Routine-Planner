package android.hromovych.com.routineplanner.data.entities

import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.room.Entity

@Entity(tableName = "weekday_doing", primaryKeys = ["weekday", "doingId"])
data class WeekdayDoing(
    var weekday: Weekday,
    var doingId: Long,
    var position: Int
)