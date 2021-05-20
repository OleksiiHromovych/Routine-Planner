package android.hromovych.com.routineplanner.data.mapper.toEntity

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.Template as TemplateEntity

object TemplateToEntityMapper : Mapper<Template, TemplateEntity> {

    override fun convert(obj: Template): TemplateEntity {
        return TemplateEntity(
            id = obj.id,
            name = obj.name
        )
    }

}