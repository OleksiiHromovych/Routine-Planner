package android.hromovych.com.routineplanner.data.mapper.fromEntity

import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper

object TemplateToPresentationMapper : Mapper<TemplateWithFullDoings, Template> {

    override fun convert(obj: TemplateWithFullDoings): Template {
        return Template(
            obj.template.id,
            obj.template.name,
            obj.doings.map {
                DoingTemplateToPresentationMapper.convert(it)
            }
        )
    }

}