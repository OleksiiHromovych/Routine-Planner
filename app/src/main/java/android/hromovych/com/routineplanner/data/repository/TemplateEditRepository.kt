package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.template_edit.TemplateEditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TemplateEditRepositoryImp(
    private val db: PlannerDatabase,
    private val templateToPresentationMapper: Mapper<TemplateWithFullDoings, Template>,
    private val presentationToEntityMapper: Mapper<DoingTemplate, android.hromovych.com.routineplanner.data.entities.DoingTemplate>,
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