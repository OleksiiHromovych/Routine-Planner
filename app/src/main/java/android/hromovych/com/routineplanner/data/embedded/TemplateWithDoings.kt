package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.room.Embedded
import androidx.room.Relation

data class TemplateWithDoings(
    @Embedded var template: Template,
    @Relation(
        parentColumn = "id",
        entityColumn = "templateId"
    )
    val doings: List<DoingTemplate>
)