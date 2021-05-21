package android.hromovych.com.routineplanner.domain.repository.templates

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class AddTemplateUseCase(
    private val templatesRepository: TemplatesRepository,
)  : CoroutineUseCase<Template, Long>(){

    override suspend fun execute(parameters: Template): Long {
        return templatesRepository.addTemplate(parameters)
    }
}