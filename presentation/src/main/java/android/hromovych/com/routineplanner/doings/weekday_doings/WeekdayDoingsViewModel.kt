package android.hromovych.com.routineplanner.doings.weekday_doings

import androidx.lifecycle.*
import com.hromovych.domain.entity.Doing
import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.repository.doings.AddDoingUseCase
import com.hromovych.domain.repository.doings.GetActiveDoingsUseCase
import com.hromovych.domain.repository.doings.UpdateDoingUseCase
import com.hromovych.domain.repository.weekday_doings.AddWeekdayDoingsUseCase
import com.hromovych.domain.repository.weekday_doings.DeleteWeekdayDoingUseCase
import com.hromovych.domain.repository.weekday_doings.GetWeekdayDoingsUseCase
import com.hromovych.domain.repository.weekday_doings.UpdateWeekdayDoingUseCase
import com.hromovych.domain.utils.Weekday
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeekdayDoingsViewModel(
    private val addWeekdayDoingsUseCase: AddWeekdayDoingsUseCase,
    private val deleteWeekdayDoingUseCase: DeleteWeekdayDoingUseCase,
    private val getWeekdayDoingsUseCase: GetWeekdayDoingsUseCase,
    private val updateWeekdayDoingUseCase: UpdateWeekdayDoingUseCase,
    private val getActiveDoingsUseCase: GetActiveDoingsUseCase,
    private val addDoingUseCase: AddDoingUseCase,
    private val updateDoingUseCase: UpdateDoingUseCase,
) : ViewModel() {

    private val _weekday: MutableLiveData<Weekday> = MutableLiveData(Weekday.Monday)
    val weekday: LiveData<Weekday>
        get() = _weekday

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val doings: LiveData<List<WeekdayDoing>> = Transformations.switchMap(_weekday) { weekday ->
        getWeekdayDoingsUseCase(weekday).asLiveData()
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addNewWeekDoing(doing: Doing) {
        viewModelScope.launch {
            addDoingUseCase(doing).also { doing.id = it }

            val weekdayDoing = WeekdayDoing(
                weekday = _weekday.value!!,
                doing = doing,
                position = doings.value!!.size
            )

            addWeekdayDoingsUseCase(arrayOf(weekdayDoing))
        }
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            updateDoingUseCase(doing)
        }
    }

    fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing) {
        viewModelScope.launch {
            deleteWeekdayDoingUseCase(weekdayDoing)
        }
    }

    fun changeWeekday(weekday: Weekday) {
        _weekday.value = weekday
    }

    fun receiveNotUsedDoings(onReceive: (List<Doing>) -> Unit) {
        val usedDoingsIds = doings.value?.map { it.doing.id } ?: emptyList()
        viewModelScope.launch {
            val result = getActiveDoingsUseCase(Unit).filterNot { it.id in usedDoingsIds }

            onReceive(result)
        }
    }

    fun addWeekdayDoings(newDoings: List<Doing>) {
        viewModelScope.launch {
            val currentListSize = doings.value?.size ?: 0

            val newWeekdayDoings = newDoings.mapIndexed { index, doing ->
                WeekdayDoing(
                    weekday = weekday.value!!,
                    doing = doing,
                    position = currentListSize + index
                )
            }

            addWeekdayDoingsUseCase(newWeekdayDoings.toTypedArray())
        }
    }

    fun updateWeekdayDoings(weekdayDoings: List<WeekdayDoing>) {
        viewModelScope.launch {
            updateWeekdayDoingUseCase(weekdayDoings)
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}