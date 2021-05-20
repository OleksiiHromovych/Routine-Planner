package android.hromovych.com.routineplanner.data.mapper.fromEntity

import android.hromovych.com.routineplanner.data.embedded.FullDoingTemplate
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.mapper.Mapper

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