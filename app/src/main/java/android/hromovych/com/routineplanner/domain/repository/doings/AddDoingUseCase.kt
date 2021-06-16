package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class AddDoingUseCase(
    private val doingsRepository: DoingsRepository
): CoroutineUseCase<Doing, Long>() {

    override suspend fun execute(parameters: Doing): Long {
        return doingsRepository.addDoing(parameters)
    }

}