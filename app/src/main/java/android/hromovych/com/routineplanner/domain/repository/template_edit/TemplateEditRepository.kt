package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import kotlinx.coroutines.flow.Flow

interface TemplateEditRepository {

    fun getTemplateWithDoings(templateId: Long): Flow<Template>

    suspend fun addTemplateDoings(vararg templateDoing: DoingTemplate)

    suspend fun deleteTemplateDoing(templateDoing: DoingTemplate)

    suspend fun updateTemplateDoings(templateDoing: List<DoingTemplate>)

}