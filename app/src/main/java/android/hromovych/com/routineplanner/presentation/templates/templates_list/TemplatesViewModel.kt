package android.hromovych.com.routineplanner.presentation.templates.templates_list

import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import android.hromovych.com.routineplanner.data.mapper.fromEntity.TemplateToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.TemplateToEntityMapper
import android.hromovych.com.routineplanner.domain.entity.Template
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplatesViewModel(dataSource: TemplatesDbDao) : ViewModel() {

    private val database = dataSource

    val templates: LiveData<List<Template>> = database.getTemplatesWithFullDoings().map { list ->
        list.map { TemplateToPresentationMapper.convert(it) }
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun navigateToTemplateEdit(template: Template) {
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToTemplateEdit(template.id))
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addTemplate(template: Template) {
        viewModelScope.launch {
            val templateEntity = TemplateToEntityMapper.convert(template)
            database.addTemplate(templateEntity)
        }
    }

    sealed class Event{
        data class NavigateToTemplateEdit(val templateID: Long): Event()
        data class ShowToast(val text: String): Event()
        object OnFabClicked : Event()
    }
}