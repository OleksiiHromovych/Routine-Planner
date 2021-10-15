package com.hromovych.data.mapper.toEntity

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.DoingTemplate as DoingTemplateEntity

object DoingTemplateToEntityMapper: Mapper<DoingTemplate, DoingTemplateEntity> {

    override fun convert(obj: DoingTemplate): DoingTemplateEntity {
        return DoingTemplateEntity(
            _id = obj.id,
            templateId = obj.templateId,
            doingId = obj.doing.id,
            position = obj.position
        )
    }
}