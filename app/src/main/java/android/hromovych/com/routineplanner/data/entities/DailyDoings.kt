package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity
import java.util.*

@Entity(tableName = "daily_doings")
data class DailyDoings(

    var date: Date,
    var doingId: Long,
    var position: Int = 0,
    var completed: Boolean = false

)
