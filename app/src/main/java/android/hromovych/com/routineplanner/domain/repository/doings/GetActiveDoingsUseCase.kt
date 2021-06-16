package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class GetActiveDoingsUseCase(
    private val doingsRepository: DoingsRepository,
) : CoroutineUseCase<Unit, List<Doing>>() {

    override suspend fun execute(parameters: Unit): List<Doing> {
        return doingsRepository.getActiveDoings()
    }

}