package com.hromovych.data.repository

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.data.embedded.TemplateWithFullDoings
import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.entity.Template
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.template_edit.TemplateEditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TemplateEditRepositoryImp(
    private val db: PlannerDatabase,
    private val templateToPresentationMapper: Mapper<TemplateWithFullDoings, Template>,
    private val presentationToEntityMapper: Mapper<DoingTemplate, com.hromovych.data.entities.DoingTemplate>,
) : TemplateEditRepository {

    override fun getTemplateWithDoings(templateId: Long): Flow<Template> {
        return db.templatesDbDao.getTemplateWithFullDoings(templateId)
            .map(templateToPresentationMapper::convert)
    }

    override suspend fun addTemplateDoings(vararg templateDoing: DoingTemplate) {
        db.templatesDbDao.addAllTemplateDoings(*templateDoing.map(presentationToEntityMapper::convert)
            .toTypedArray())
    }

    override suspend fun deleteTemplateDoing(templateDoing: DoingTemplate) {
        db.templatesDbDao.deleteDoingTemplate(presentationToEntityMapper.convert(templateDoing))
    }

    override suspend fun updateTemplateDoings(templateDoings: List<DoingTemplate>) {
        db.templatesDbDao.updateDoingsTemplate(templateDoings.map(presentationToEntityMapper::convert))
    }

}