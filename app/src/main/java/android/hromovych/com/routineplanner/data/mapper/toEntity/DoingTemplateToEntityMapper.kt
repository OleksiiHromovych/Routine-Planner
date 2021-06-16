package android.hromovych.com.routineplanner.data.mapper.toEntity

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.DoingTemplate as DoingTemplateEntity

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