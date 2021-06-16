package android.hromovych.com.routineplanner.domain.repository.daily_doings

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class AddDailyDoingsUseCase(
    private val dailyDoingsRepository: DailyDoingsRepository
) : CoroutineUseCase<Array<DailyDoing>, Unit>(){

    override suspend fun execute(parameters: Array<DailyDoing>) {
        dailyDoingsRepository.addDailyDoings(*parameters)
    }

}