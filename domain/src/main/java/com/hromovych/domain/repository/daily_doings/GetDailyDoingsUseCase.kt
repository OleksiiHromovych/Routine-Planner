package com.hromovych.domain.repository.daily_doings

import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetDailyDoingsUseCase(
    private val dailyDoingsRepository: DailyDoingsRepository,
    dispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, List<DailyDoing>>(dispatcher) {

    override fun execute(parameters: Int): Flow<List<DailyDoing>> {
        return dailyDoingsRepository.getDailyDoings(parameters)
    }

}