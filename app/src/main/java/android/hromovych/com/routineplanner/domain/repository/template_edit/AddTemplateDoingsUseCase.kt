package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class AddTemplateDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository,
) : CoroutineUseCase<Array<out DoingTemplate>, Unit>() {

    override suspend fun execute(parameters: Array<out DoingTemplate>) {
        templateEditRepository.addTemplateDoings(*parameters)
    }

}