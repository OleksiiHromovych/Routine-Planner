package android.hromovych.com.routineplanner.presentation.templates.templates_list

import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplatesViewModel(dataSource: TemplatesDbDao) : ViewModel() {

    private val database = dataSource

//    private lateinit var _templates: MutableLiveData<List<TemplateWithFullDoings>>
    val templates: LiveData<List<TemplateWithFullDoings>> = database.getTemplatesWithFullDoings()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
//
//    init {
//        initializeTemplate()
//    }
//
//    private fun initializeTemplate() {
//        viewModelScope.launch {
//            _templates = database.getTemplatesWithFullDoings()
//        }
//    }

    fun navigateToTemplateEdit(template: TemplateWithFullDoings) {
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToTemplateEdit(template.template.id))
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addTemplate(template: Template) {
        viewModelScope.launch {
            database.addTemplate(template)
        }
    }

    sealed class Event{
        data class NavigateToTemplateEdit(val templateID: Long): Event()
        data class ShowToast(val text: String): Event()
        object OnFabClicked : Event()
    }
}