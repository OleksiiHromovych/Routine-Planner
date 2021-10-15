package com.hromovych.data.mapper.fromEntity

import com.hromovych.data.embedded.FullDoingTemplate
import com.hromovych.data.embedded.TemplateWithFullDoings
import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.entity.Template
import com.hromovych.domain.mapper.Mapper

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