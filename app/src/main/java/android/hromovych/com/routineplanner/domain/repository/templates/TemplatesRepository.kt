package android.hromovych.com.routineplanner.domain.repository.templates

import android.hromovych.com.routineplanner.domain.entity.Template
import androidx.lifecycle.LiveData

interface TemplatesRepository {

    suspend fun addTemplate(template: Template): Long

    fun getTemplatesWithFullDoings(): LiveData<List<Template>>

    suspend fun deleteTemplate(template: Template)

    suspend fun updateTemplate(template: Template)
}