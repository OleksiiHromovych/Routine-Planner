package com.hromovych.domain.repository.doings

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.usecase.CoroutineUseCase

class GetActiveDoingsUseCase(
    private val doingsRepository: DoingsRepository,
) : CoroutineUseCase<Unit, List<Doing>>() {

    override suspend fun execute(parameters: Unit): List<Doing> {
        return doingsRepository.getActiveDoings()
    }

}