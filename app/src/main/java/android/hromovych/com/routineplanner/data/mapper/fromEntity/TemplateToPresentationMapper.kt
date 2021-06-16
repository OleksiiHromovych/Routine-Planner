package android.hromovych.com.routineplanner.data.mapper.fromEntity

import android.hromovych.com.routineplanner.data.embedded.FullDoingTemplate
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper

class TemplateToPresentationMapper(
    private val doingTemplateToPresentationMapper: Mapper<FullDoingTemplate, DoingTemplate>,
) : Mapper<TemplateWithFullDoings, Template> {

    override fun convert(obj: TemplateWithFullDoings): Template {
        return Template(
            obj.template.id,
            obj.template.name,
            obj.doings.map(doingTemplateToPresentationMapper::convert)
        )
    }

}