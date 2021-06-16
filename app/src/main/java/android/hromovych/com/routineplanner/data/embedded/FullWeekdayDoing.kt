package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing
import androidx.room.Embedded
import androidx.room.Relation

data class FullWeekdayDoing(
    @Embedded val weekdayDoing: WeekdayDoing,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    val doing: Doing
)