package com.hromovych.domain.repository.weekday_doings

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.usecase.FlowUseCase
import com.hromovych.domain.utils.Weekday
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetWeekdayDoingsUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
    dispatcher: CoroutineDispatcher
) : FlowUseCase<Weekday, List<WeekdayDoing>>(dispatcher){

    override fun execute(parameters: Weekday): Flow<List<WeekdayDoing>> {
        return weekdayDoingsRepository.getWeekdayDoings(parameters)
    }

}