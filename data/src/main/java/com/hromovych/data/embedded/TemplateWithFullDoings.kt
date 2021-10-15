package com.hromovych.data.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.hromovych.data.entities.DoingTemplate
import com.hromovych.data.entities.Template

data class TemplateWithFullDoings(
    @Embedded var template: Template,
    @Relation(
        entity = DoingTemplate::class,
        parentColumn = "id",
        entityColumn = "templateId"
    )
    val doings: List<FullDoingTemplate>
)