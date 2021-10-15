package com.hromovych.domain.repository.template_edit

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.usecase.CoroutineUseCase

class AddTemplateDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository,
) : CoroutineUseCase<Array<out DoingTemplate>, Unit>() {

    override suspend fun execute(parameters: Array<out DoingTemplate>) {
        templateEditRepository.addTemplateDoings(*parameters)
    }

}