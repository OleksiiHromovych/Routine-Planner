package android.hromovych.com.routineplanner.data.embedded

import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.room.Embedded
import androidx.room.Relation

data class TemplateDoing(
    @Embedded var doingTemplate: DoingTemplate,
    @Relation(
        parentColumn = "templateId",
        entityColumn = "id"
    )
    var template: Template,
    @Relation(
        parentColumn = "doingId",
        entityColumn = "id"
    )
    var doing: Doing
) {
    val title: String
        get() = doing.title

    val templateName: String
        get() = template.name

    val position: Int
        get() = doingTemplate.position
}
