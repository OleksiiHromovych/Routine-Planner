package com.hromovych.domain.repository.templates

import com.hromovych.domain.entity.Template
import com.hromovych.domain.usecase.CoroutineUseCase

class AddTemplateUseCase(
    private val templatesRepository: TemplatesRepository,
)  : CoroutineUseCase<Template, Long>(){

    override suspend fun execute(parameters: Template): Long {
        return templatesRepository.addTemplate(parameters)
    }
}