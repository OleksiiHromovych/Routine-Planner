package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.entities.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.templates.TemplatesRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import android.hromovych.com.routineplanner.domain.entity.Template as PresentationTemplate


class TemplatesRepositoryImpl(
    private val db: PlannerDatabase,
    private val entityToPresentationMapper: Mapper<TemplateWithFullDoings, PresentationTemplate>,
    private val presentationToEntityMapper: Mapper<PresentationTemplate, Template>

) : TemplatesRepository {

    override suspend fun addTemplate(template: PresentationTemplate): Long {
        return db.templatesDbDao.addTemplate(presentationToEntityMapper.convert(template))
    }

    override fun getTemplatesWithFullDoings(): LiveData<List<PresentationTemplate>> {
        return db.templatesDbDao.getTemplatesWithFullDoings().map { list ->
            list.map(entityToPresentationMapper::convert)
        }
    }

    override suspend fun deleteTemplate(template: PresentationTemplate) {
        db.templatesDbDao.deleteTemplate(presentationToEntityMapper.convert(template))
    }

    override suspend fun updateTemplate(template: android.hromovych.com.routineplanner.domain.entity.Template) {
        db.templatesDbDao.updateTemplate(presentationToEntityMapper.convert(template))
    }

}