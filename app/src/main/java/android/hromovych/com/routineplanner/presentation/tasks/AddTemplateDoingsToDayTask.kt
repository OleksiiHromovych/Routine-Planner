package android.hromovych.com.routineplanner.presentation.tasks

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.repository.daily_doings.AddDailyDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.daily_doings.GetDailyDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.GetTemplateWithDoingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AddTemplateDoingsToDayTask(
    private val getTemplateWithDoingsUseCase: GetTemplateWithDoingsUseCase,
    private val getDailyDoingsUseCase: GetDailyDoingsUseCase,
    private val addDailyDoingsUseCase: AddDailyDoingsUseCase,
) : BasicTask<AddTemplateDoingsToDayTask.Param> {

    override fun start(parameters: Param) {
        val newDoingsFlow = getTemplateWithDoingsUseCase(parameters.templateId)
            .combine(getDailyDoingsUseCase(parameters.datePattern)) { template, dailyDoings ->
                val existsDoingIds = dailyDoings.map { it.doing.id }
                template.doings.filter { it.doing.id !in existsDoingIds }.map {
                    DailyDoing(
                        date = parameters.datePattern,
                        doing = it.doing,
                        position = existsDoingIds.size + it.position
                    )
                }

            }

        CoroutineScope(Dispatchers.IO).launch {
            newDoingsFlow.collectLatest { addDailyDoingsUseCase(it.toTypedArray()) }
        }
    }

    data class Param(val templateId: Long, val datePattern: Int)

}