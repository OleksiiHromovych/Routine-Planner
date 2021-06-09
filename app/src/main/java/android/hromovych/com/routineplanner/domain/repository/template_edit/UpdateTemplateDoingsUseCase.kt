package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class UpdateTemplateDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository,
) : CoroutineUseCase<List<DoingTemplate>, Unit>() {

    override suspend fun execute(parameters: List<DoingTemplate>) {
        templateEditRepository.updateTemplateDoings(parameters)
    }

}