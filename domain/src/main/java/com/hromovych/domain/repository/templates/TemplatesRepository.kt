package com.hromovych.domain.repository.templates

import com.hromovych.domain.entity.Template
import kotlinx.coroutines.flow.Flow

interface TemplatesRepository {

    suspend fun addTemplate(template: Template): Long

    fun getTemplatesWithFullDoings(): Flow<List<Template>>

    suspend fun deleteTemplate(template: Template)

    suspend fun updateTemplate(template: Template)
}