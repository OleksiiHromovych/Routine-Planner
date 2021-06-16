package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.usecase.UseCase
import androidx.lifecycle.LiveData

class GetDoingsUseCase(
    private val doingsRepository: DoingsRepository
) : UseCase<Unit, LiveData<List<Doing>>>() {

    override fun execute(parameters: Unit): LiveData<List<Doing>> {
        return doingsRepository.getDoings()
    }
}