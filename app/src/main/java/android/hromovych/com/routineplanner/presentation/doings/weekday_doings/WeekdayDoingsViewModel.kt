package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.repository.doings.AddDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.GetActiveDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.UpdateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.AddWeekdayDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.DeleteWeekdayDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.GetWeekdayDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.UpdateWeekdayDoingUseCase
import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.lifecycle.*
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
        getWeekdayDoingsUseCase(weekday)
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
            val result = getActiveDoingsUseCase(Unit).filterNot { it.id in usedDoingsIds}

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

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}