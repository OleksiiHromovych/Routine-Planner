package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTemplateWithDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository,
    dispatcher: CoroutineDispatcher
): FlowUseCase<Long, Template>(dispatcher) {

    override fun execute(parameters: Long): Flow<Template> {
        return templateEditRepository.getTemplateWithDoings(parameters).map {
            it.apply {
                doings = doings.sortedBy { it.position }
            }
        }
    }

}