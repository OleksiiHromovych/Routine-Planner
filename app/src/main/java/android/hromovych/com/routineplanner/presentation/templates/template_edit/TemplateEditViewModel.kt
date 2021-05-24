package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import android.hromovych.com.routineplanner.data.mapper.fromEntity.DoingToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.fromEntity.TemplateToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DoingTemplateToEntityMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DoingToEntityMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.TemplateToEntityMapper
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.entity.Template
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplateEditViewModel(
    private val templateId: Long,
    templateDao: TemplatesDbDao,
    doingsDbDao: DoingsDbDao,
) : ViewModel() {

    private val templateBase = templateDao
    private val doingsBase = doingsDbDao

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val template: LiveData<Template> =
        templateBase.getTemplateWithFullDoings(templateId).map {
            TemplateToPresentationMapper.convert(it)
        }

    val templateName = template.map {
        it.name
    }

    val templateDoings = template.map {
        it.doings
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            doingsBase.updateDoing(doingEntity)
        }
    }

    fun deleteTemplateDoing(doingTemplate: DoingTemplate) {
        viewModelScope.launch {
            val doingTemplateEntity = DoingTemplateToEntityMapper.convert(doingTemplate)
            templateBase.deleteDoingTemplate(doingTemplateEntity)
        }
    }


    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun deleteCurrentTemplate() {
        viewModelScope.launch {
            templateBase.deleteTemplate(templateId)
        }
    }

    val updateTemplateName: (String) -> Unit = { newName: String ->
        viewModelScope.launch {
            val template = Template(templateId, newName)
            val templateEntity = TemplateToEntityMapper.convert(template)
            templateBase.updateTemplate(templateEntity)
        }
    }

    fun addNewTemplateDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            doingsBase.addDoing(doingEntity).also { doing.id = it }

            val templateDoing = DoingTemplate(
                templateId = templateId,
                doing = doing,
                position = templateDoings.value?.size ?: 0
            )
            val templateDoingEntity = DoingTemplateToEntityMapper.convert(templateDoing)
            templateBase.addTemplateDoing(templateDoingEntity)
        }
    }

    fun receiveNotUsedDoings(onReceive: (List<Doing>) -> Unit) {
        viewModelScope.launch {
            val newDoings = templateBase.getNewTemplateDoingsForTemplate(templateId)
                .map(DoingToPresentationMapper::convert)
            onReceive(newDoings)
        }
    }

    fun addTemplateDoings(newDoings: List<Doing>) {
        viewModelScope.launch {
            val currentListSize = templateDoings.value?.size ?: 0

            val newTemplateDoings = newDoings.mapIndexed { index, doing ->
                val item = DoingTemplate(
                    templateId = templateId,
                    doing = doing,
                    position = currentListSize + index
                )
                DoingTemplateToEntityMapper.convert(item)
            }

            templateBase.addAllTemplateDoings(*newTemplateDoings.toTypedArray())
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}