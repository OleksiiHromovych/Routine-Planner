package com.hromovych.data.repository

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.data.embedded.TemplateWithFullDoings
import com.hromovych.data.entities.Template
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.templates.TemplatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.hromovych.domain.entity.Template as PresentationTemplate


class TemplatesRepositoryImpl(
    private val db: PlannerDatabase,
    private val entityToPresentationMapper: Mapper<TemplateWithFullDoings, PresentationTemplate>,
    private val presentationToEntityMapper: Mapper<PresentationTemplate, Template>

) : TemplatesRepository {

    override suspend fun addTemplate(template: PresentationTemplate): Long {
        return db.templatesDbDao.addTemplate(presentationToEntityMapper.convert(template))
    }

    override fun getTemplatesWithFullDoings(): Flow<List<PresentationTemplate>> {
        return db.templatesDbDao.getTemplatesWithFullDoings().map { list ->
            list.map(entityToPresentationMapper::convert)
        }
    }

    override suspend fun deleteTemplate(template: PresentationTemplate) {
        db.templatesDbDao.deleteTemplate(presentationToEntityMapper.convert(template))
    }

    override suspend fun updateTemplate(template: com.hromovych.domain.entity.Template) {
        db.templatesDbDao.updateTemplate(presentationToEntityMapper.convert(template))
    }

}