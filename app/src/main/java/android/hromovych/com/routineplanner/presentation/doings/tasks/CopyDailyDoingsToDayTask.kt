package android.hromovych.com.routineplanner.presentation.doings.tasks

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.repository.daily_doings.AddDailyDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.daily_doings.GetDailyDoingsUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class CopyDailyDoingsToDayTask(
    private val getDailyDoingsUseCase: GetDailyDoingsUseCase,
    private val addDailyDoingsUseCase: AddDailyDoingsUseCase,
) : CoroutineBasicTask<CopyDailyDoingsToDayTask.Param> {

    override suspend fun start(parameters: Param) {
        val toDayDoings = getDailyDoingsUseCase(parameters.toDay)
        val fromDayDoings = getDailyDoingsUseCase(parameters.fromDay)

        val newDailyDoings = fromDayDoings.combine(toDayDoings) { fromDoings, toDoings ->
            val toDoingsIds = toDoings.map { it.doing.id }
            fromDoings.filter { it.doing.id !in toDoingsIds }
                .mapIndexed { index, dailyDoing ->
                    DailyDoing(
                        date = parameters.toDay,
                        doing = dailyDoing.doing,
                        position = toDoings.size + index
                    )
                }
        }

        addDailyDoingsUseCase(newDailyDoings.first().toTypedArray())
    }

    data class Param(
        val fromDay: Int,
        val toDay: Int,
    )
}