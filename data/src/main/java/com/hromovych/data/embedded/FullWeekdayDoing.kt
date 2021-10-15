package com.hromovych.data.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.hromovych.data.entities.Doing
import com.hromovych.data.entities.WeekdayDoing

data class FullWeekdayDoing(
    @Embedded val weekdayDoing: WeekdayDoing,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    val doing: Doing
)