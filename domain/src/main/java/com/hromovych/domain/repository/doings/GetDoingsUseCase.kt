package com.hromovych.domain.repository.doings

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetDoingsUseCase(
    private val doingsRepository: DoingsRepository,
    dispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Doing>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<List<Doing>> {
        return doingsRepository.getDoings()
    }
}