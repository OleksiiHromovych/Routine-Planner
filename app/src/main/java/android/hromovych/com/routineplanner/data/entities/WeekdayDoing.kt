package android.hromovych.com.routineplanner.data.entities

import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekday_doing")
data class WeekdayDoing(
    @PrimaryKey(autoGenerate = true) var _id: Long,
    var weekday: Weekday,
    var doingId: Long,
    var position: Int
)