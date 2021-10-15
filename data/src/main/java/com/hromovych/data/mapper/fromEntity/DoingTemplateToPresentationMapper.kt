package com.hromovych.data.mapper.fromEntity

import com.hromovych.data.embedded.FullDoingTemplate
import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.mapper.Mapper

object DoingTemplateToPresentationMapper: Mapper<FullDoingTemplate, DoingTemplate> {

    override fun convert(obj: FullDoingTemplate): DoingTemplate {
        return DoingTemplate(
            id = obj.doingTemplate._id,
            doing = DoingToPresentationMapper.convert(obj.doing),
            position = obj.doingTemplate.position,
            templateId = obj.doingTemplate.templateId
        )
    }

}