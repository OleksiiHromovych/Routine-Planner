package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.usecase.CoroutineUseCase

class DeleteWeekdayDoingUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository,
) : CoroutineUseCase<WeekdayDoing, Unit>() {

    override suspend fun execute(parameters: WeekdayDoing) {
        weekdayDoingsRepository.deleteWeekdayDoing(parameters)
    }

}