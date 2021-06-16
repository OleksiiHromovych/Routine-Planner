package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.room.Embedded
import androidx.room.Relation

data class TemplateWithFullDoings(
    @Embedded var template: Template,
    @Relation(
        entity = DoingTemplate::class,
        parentColumn = "id",
        entityColumn = "templateId"
    )
    val doings: List<FullDoingTemplate>
)