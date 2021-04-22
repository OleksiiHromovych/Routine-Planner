package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplateEditViewModel(
    val templateId: Long,
    templateDao: TemplatesDbDao,
    doingsDbDao: DoingsDbDao
) : ViewModel() {

    private val templateBase = templateDao
    private val doingsBase = doingsDbDao

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val templateWithFullDoings = templateBase.getTemplateWithFullDoings(templateId)

    val templateName = Transformations.map(templateWithFullDoings) {
        it.template.name
    }

    val templateDoings = Transformations.map(templateWithFullDoings) {
        it.doings
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            doingsBase.updateDoing(doing)
        }
    }

    fun deleteTemplateDoing(doingTemplate: DoingTemplate) {
        viewModelScope.launch {
            templateBase.deleteDoingTemplate(doingTemplate)
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
            templateBase.updateTemplate(template)
        }
    }

    fun addNewTemplateDoing(doing: Doing) {
        viewModelScope.launch {
            val doingId = doingsBase.addDoing(doing)
            val templateDoing = DoingTemplate(templateId = templateId, doingId = doingId)
            templateBase.addTemplateDoing(templateDoing)
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}