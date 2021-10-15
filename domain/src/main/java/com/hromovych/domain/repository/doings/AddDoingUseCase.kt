package com.hromovych.domain.repository.doings

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.usecase.CoroutineUseCase

class AddDoingUseCase(
    private val doingsRepository: DoingsRepository
): CoroutineUseCase<Doing, Long>() {

    override suspend fun execute(parameters: Doing): Long {
        return doingsRepository.addDoing(parameters)
    }

}