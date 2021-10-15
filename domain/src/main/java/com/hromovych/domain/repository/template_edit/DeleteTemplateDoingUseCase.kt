package com.hromovych.domain.repository.template_edit

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.usecase.CoroutineUseCase

class DeleteTemplateDoingUseCase(
    private val templateEditRepository: TemplateEditRepository
): CoroutineUseCase<DoingTemplate, Unit>() {

    override suspend fun execute(parameters: DoingTemplate) {
        templateEditRepository.deleteTemplateDoing(parameters)
    }

}