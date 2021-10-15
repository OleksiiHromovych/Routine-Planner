package com.hromovych.domain.repository.daily_doings

import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.usecase.CoroutineUseCase

class AddDailyDoingsUseCase(
    private val dailyDoingsRepository: DailyDoingsRepository
) : CoroutineUseCase<Array<DailyDoing>, Unit>(){

    override suspend fun execute(parameters: Array<DailyDoing>) {
        dailyDoingsRepository.addDailyDoings(*parameters)
    }

}