package android.hromovych.com.routineplanner.domain.repository.templates

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class DeleteTemplateUseCase(
    private val templatesRepository: TemplatesRepository,
) : CoroutineUseCase<Template, Unit>() {

    override suspend fun execute(parameters: Template) {
        templatesRepository.deleteTemplate(parameters)
    }

}