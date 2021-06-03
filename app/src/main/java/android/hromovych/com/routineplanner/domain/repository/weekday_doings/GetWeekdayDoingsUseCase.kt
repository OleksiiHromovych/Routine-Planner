package android.hromovych.com.routineplanner.domain.repository.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.usecase.UseCase
import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.lifecycle.LiveData

class GetWeekdayDoingsUseCase(
    private val weekdayDoingsRepository: WeekdayDoingsRepository
) : UseCase<Weekday, LiveData<List<WeekdayDoing>>>(){

    override fun execute(parameters: Weekday): LiveData<List<WeekdayDoing>> {
        return weekdayDoingsRepository.getWeekdayDoings(parameters)
    }

}