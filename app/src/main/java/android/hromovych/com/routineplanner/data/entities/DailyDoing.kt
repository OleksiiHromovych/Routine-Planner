package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_doings")
data class DailyDoing(
    @PrimaryKey(autoGenerate = true) var _id: Long = 0,
    var date: Int,
    var doingId: Long,
    var position: Int = 0,
    var completed: Boolean = false

)
