package com.hromovych.domain.repository.templates

import com.hromovych.domain.entity.Template
import com.hromovych.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTemplatesWithFullDoingsUseCase(
    private val templatesRepository: TemplatesRepository,
    dispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Template>>(dispatcher) {

    operator fun invoke() = super.invoke(Unit)

    override fun execute(parameters: Unit): Flow<List<Template>> {
        return templatesRepository.getTemplatesWithFullDoings().map {
            it.map { template ->
                template.apply {
                    doings = doings.sortedBy { it.position }
                }
            }
        }
    }
}