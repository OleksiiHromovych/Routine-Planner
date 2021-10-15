package com.hromovych.domain.repository.templates

import com.hromovych.domain.entity.Template
import com.hromovych.domain.usecase.CoroutineUseCase

class UpdateTemplateUseCase(
    private val templatesRepository: TemplatesRepository
) : CoroutineUseCase<Template, Unit>() {

    override suspend fun execute(parameters: Template) {
        templatesRepository.updateTemplate(parameters)
    }
}