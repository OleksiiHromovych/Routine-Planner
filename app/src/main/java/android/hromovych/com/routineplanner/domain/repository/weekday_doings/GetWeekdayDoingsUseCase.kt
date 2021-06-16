package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.usecase.FlowUseCase
import android.hromovych.com.routineplanner.domain.utils.Weekday
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