package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import androidx.lifecycle.LiveData

interface TemplateEditRepository {

    fun getTemplateWithDoings(templateId: Long): LiveData<Template>

    suspend fun addTemplateDoings(vararg templateDoing: DoingTemplate)

    suspend fun deleteTemplateDoing(templateDoing: DoingTemplate)

    suspend fun updateTemplateDoings(templateDoing: List<DoingTemplate>)

}