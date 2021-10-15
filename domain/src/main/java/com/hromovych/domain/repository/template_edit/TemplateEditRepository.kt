package com.hromovych.domain.repository.template_edit

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.entity.Template
import kotlinx.coroutines.flow.Flow

interface TemplateEditRepository {

    fun getTemplateWithDoings(templateId: Long): Flow<Template>

    suspend fun addTemplateDoings(vararg templateDoing: DoingTemplate)

    suspend fun deleteTemplateDoing(templateDoing: DoingTemplate)

    suspend fun updateTemplateDoings(templateDoing: List<DoingTemplate>)

}