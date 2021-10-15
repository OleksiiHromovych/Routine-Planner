package com.hromovych.domain.repository.template_edit

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.usecase.CoroutineUseCase

class UpdateTemplateDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository,
) : CoroutineUseCase<List<DoingTemplate>, Unit>() {

    override suspend fun execute(parameters: List<DoingTemplate>) {
        templateEditRepository.updateTemplateDoings(parameters)
    }

}