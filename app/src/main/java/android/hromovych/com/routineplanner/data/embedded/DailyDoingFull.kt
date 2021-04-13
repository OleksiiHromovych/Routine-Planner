package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.DailyDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import androidx.room.Embedded
import androidx.room.Relation

data class DailyDoingFull(
    @Embedded var dailyDoing: DailyDoing,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    var doing: Doing
)
