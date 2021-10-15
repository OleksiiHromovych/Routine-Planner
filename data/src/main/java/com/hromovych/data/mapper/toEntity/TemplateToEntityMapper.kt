package com.hromovych.data.mapper.toEntity

import com.hromovych.domain.entity.Template
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.Template as TemplateEntity

object TemplateToEntityMapper : Mapper<Template, TemplateEntity> {

    override fun convert(obj: Template): TemplateEntity {
        return TemplateEntity(
            id = obj.id,
            name = obj.name
        )
    }

}