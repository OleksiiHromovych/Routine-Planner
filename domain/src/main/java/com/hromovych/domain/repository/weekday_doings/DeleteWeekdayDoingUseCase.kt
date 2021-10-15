package com.hromovych.domain.repository.weekday_doings

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.usecase.CoroutineUseCase

class DeleteWeekdayDoingUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<WeekdayDoing, Unit>() {

    override suspend fun execute(parameters: WeekdayDoing) {
        weekdayDoingsRepository.deleteWeekdayDoing(parameters)
    }

}