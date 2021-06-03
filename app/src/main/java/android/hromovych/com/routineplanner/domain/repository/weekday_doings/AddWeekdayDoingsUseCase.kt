package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class AddWeekdayDoingsUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<Array<WeekdayDoing>, Unit>() {

    override suspend fun execute(parameters: Array<WeekdayDoing>) {
        weekdayDoingsRepository.addWeekdayDoings(*parameters)
    }

}