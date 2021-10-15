package com.hromovych.data.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.hromovych.data.entities.DailyDoing
import com.hromovych.data.entities.Doing

data class DailyDoingFull(
    @Embedded var dailyDoing: DailyDoing,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    var doing: Doing
)