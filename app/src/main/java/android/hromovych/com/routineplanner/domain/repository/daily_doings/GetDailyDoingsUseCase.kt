package android.hromovych.com.routineplanner.domain.repository.daily_doings

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.usecase.FlowUseCase
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