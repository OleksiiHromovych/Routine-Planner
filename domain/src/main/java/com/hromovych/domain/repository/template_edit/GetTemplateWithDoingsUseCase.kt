package com.hromovych.domain.repository.template_edit

import com.hromovych.domain.entity.Template
import com.hromovych.domain.usecase.FlowUseCase
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