package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.template_edit.TemplateEditRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class TemplateEditRepositoryImp(
    private val db: PlannerDatabase,
    private val templateToPresentationMapper: Mapper<TemplateWithFullDoings, Template>,
    private val presentationToEntityMapper: Mapper<DoingTemplate, android.hromovych.com.routineplanner.data.entities.DoingTemplate>,
) : TemplateEditRepository {

    override fun getTemplateWithDoings(templateId: Long): LiveData<Template> {
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

    override suspend fun updateTemplateDoing(templateDoing: DoingTemplate) {
        db.templatesDbDao.updateDoingTemplate(presentationToEntityMapper.convert(templateDoing))
    }

}