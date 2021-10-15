package com.hromovych.data.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.hromovych.data.entities.Doing
import com.hromovych.data.entities.DoingTemplate

data class FullDoingTemplate(
    @Embedded val doingTemplate: DoingTemplate,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    val doing: Doing
)