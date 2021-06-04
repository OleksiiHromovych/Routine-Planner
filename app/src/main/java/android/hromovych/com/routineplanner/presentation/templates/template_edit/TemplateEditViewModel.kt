package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.repository.daily_doings.AddDailyDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.AddDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.GetActiveDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.UpdateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.AddTemplateDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.DeleteTemplateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.GetTemplateWithDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.UpdateTemplateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.DeleteTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.UpdateTemplateUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplateEditViewModel(
    private val templateId: Long,
    private val addTemplateDoingsUseCase: AddTemplateDoingsUseCase,
    private val deleteTemplateDoingUseCase: DeleteTemplateDoingUseCase,
    private val getTemplateWithDoingsUseCase: GetTemplateWithDoingsUseCase,
    private val updateTemplateDoingUseCase: UpdateTemplateDoingUseCase,
    private val updateDoingUseCase: UpdateDoingUseCase,
    private val deleteTemplateUseCase: DeleteTemplateUseCase,
    private val updateTemplateUseCase: UpdateTemplateUseCase,
    private val addDoingUseCase: AddDoingUseCase,
    private val getActiveDoingsUseCase: GetActiveDoingsUseCase,
    private val addDailyDoingsUseCase: AddDailyDoingsUseCase
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val template: LiveData<Template> = getTemplateWithDoingsUseCase(templateId)

    val templateName = template.map {
        it.name
    }

    val templateDoings = template.map {
        it.doings
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            updateDoingUseCase(doing)
        }
    }

    fun deleteTemplateDoing(doingTemplate: DoingTemplate) {
        viewModelScope.launch {
            deleteTemplateDoingUseCase(doingTemplate)
        }
    }


    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun deleteCurrentTemplate() {
        viewModelScope.launch {
            deleteTemplateUseCase(template.value!!)
        }
    }

    val updateTemplateName: (String) -> Unit = { newName: String ->
        viewModelScope.launch {
            val template = Template(templateId, newName)
            updateTemplateUseCase(template)
        }
    }

    fun addNewTemplateDoing(doing: Doing) {
        viewModelScope.launch {
            addDoingUseCase(doing).also { doing.id = it }

            val templateDoing = DoingTemplate(
                templateId = templateId,
                doing = doing,
                position = templateDoings.value?.size ?: 0
            )

            addTemplateDoingsUseCase(arrayOf(templateDoing))
        }
    }

    fun receiveNotUsedDoings(onReceive: (List<Doing>) -> Unit) {
        val usedDoingsIds = templateDoings.value?.map { it.doing.id } ?: emptyList()
        viewModelScope.launch {

            val result = getActiveDoingsUseCase(Unit).filterNot { it.id in usedDoingsIds }

            onReceive(result)
        }
    }

    fun addTemplateDoings(newDoings: List<Doing>) {
        viewModelScope.launch {
            val currentListSize = templateDoings.value?.size ?: 0

            val newTemplateDoings = newDoings.mapIndexed { index, doing ->
                DoingTemplate(
                    templateId = templateId,
                    doing = doing,
                    position = currentListSize + index
                )
            }

            addTemplateDoingsUseCase(newTemplateDoings.toTypedArray())
        }
    }

    fun addTemplateDoingsToDay(datePattern: Int) {
        viewModelScope.launch {

            val dailyDoings: List<DailyDoing> = templateDoings.value?.map {
                DailyDoing(
                    date = datePattern,
                    doing = it.doing,
                    position = it.position
                )
            } ?: return@launch

            addDailyDoingsUseCase(dailyDoings.toTypedArray())
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}