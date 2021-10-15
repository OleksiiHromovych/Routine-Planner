package android.hromovych.com.routineplanner.templates.templates_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.entity.Template
import com.hromovych.domain.repository.templates.AddTemplateUseCase
import com.hromovych.domain.repository.templates.GetTemplatesWithFullDoingsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TemplatesViewModel(
    private val addTemplateUseCase: AddTemplateUseCase,
    private val getTemplatesWithFullDoingsUseCase: GetTemplatesWithFullDoingsUseCase,
) : ViewModel() {

    val templates: LiveData<List<Template>> = getTemplatesWithFullDoingsUseCase().asLiveData()

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
            addTemplateUseCase(template)
        }
    }

    sealed class Event {
        data class NavigateToTemplateEdit(val templateID: Long) : Event()
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

    companion object {

        @JvmStatic
        fun toTemplateDoingsString(doings: List<DoingTemplate>): String =
            doings.joinToString(separator = ";\n") {
                it.title
            }
    }
}