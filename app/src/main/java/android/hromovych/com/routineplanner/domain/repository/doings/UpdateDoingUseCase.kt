package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class UpdateDoingUseCase(
    private val doingsRepository: DoingsRepository
): CoroutineUseCase<Doing, Unit>() {

    override suspend fun execute(parameters: Doing) {
        doingsRepository.updateDoing(parameters)
    }

}