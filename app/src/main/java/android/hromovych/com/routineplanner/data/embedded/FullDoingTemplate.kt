package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import androidx.room.Embedded
import androidx.room.Relation

data class FullDoingTemplate(
    @Embedded val doingTemplate: DoingTemplate,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    val doing: Doing
)