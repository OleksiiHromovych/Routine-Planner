package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class UpdateWeekdayDoingUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<List<WeekdayDoing>, Unit>() {

    override suspend fun execute(parameters: List<WeekdayDoing>) {
        weekdayDoingsRepository.updateWeekdayDoings(parameters)
    }

}