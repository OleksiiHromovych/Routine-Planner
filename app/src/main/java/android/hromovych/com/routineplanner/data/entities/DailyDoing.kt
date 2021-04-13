package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity

@Entity(tableName = "daily_doings", primaryKeys = ["date", "doingId"])
data class DailyDoing(
    var date: Int,
    var doingId: Long,
    var position: Int = 0,
    var completed: Boolean = false

)
