package com.hromovych.domain.repository.weekday_doings

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.usecase.CoroutineUseCase

class AddWeekdayDoingsUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<Array<WeekdayDoing>, Unit>() {

    override suspend fun execute(parameters: Array<WeekdayDoing>) {
        weekdayDoingsRepository.addWeekdayDoings(*parameters)
    }

}