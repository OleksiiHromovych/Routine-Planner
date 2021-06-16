package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class DeleteTemplateDoingUseCase(
    private val templateEditRepository: TemplateEditRepository
): CoroutineUseCase<DoingTemplate, Unit>() {

    override suspend fun execute(parameters: DoingTemplate) {
        templateEditRepository.deleteTemplateDoing(parameters)
    }

}