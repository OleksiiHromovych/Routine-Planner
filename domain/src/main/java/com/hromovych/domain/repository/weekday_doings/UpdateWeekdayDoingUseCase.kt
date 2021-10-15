package com.hromovych.domain.repository.weekday_doings

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.usecase.CoroutineUseCase

class UpdateWeekdayDoingUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<List<WeekdayDoing>, Unit>() {

    override suspend fun execute(parameters: List<WeekdayDoing>) {
        weekdayDoingsRepository.updateWeekdayDoings(parameters)
    }

}