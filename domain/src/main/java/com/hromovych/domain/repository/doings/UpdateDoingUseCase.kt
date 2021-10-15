package com.hromovych.domain.repository.doings

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.usecase.CoroutineUseCase

class UpdateDoingUseCase(
    private val doingsRepository: DoingsRepository
): CoroutineUseCase<Doing, Unit>() {

    override suspend fun execute(parameters: Doing) {
        doingsRepository.updateDoing(parameters)
    }

}