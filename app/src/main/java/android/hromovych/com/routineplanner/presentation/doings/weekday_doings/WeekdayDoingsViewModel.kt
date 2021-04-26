package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.WeekdayDoingsDbDao
import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing
import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeekdayDoingsViewModel(
    weekdayDoingsDbDao: WeekdayDoingsDbDao,
    doingsDbDao: DoingsDbDao
) : ViewModel() {

    private val weekdayBase = weekdayDoingsDbDao
    private val doingsBase = doingsDbDao
    private val _weekday: MutableLiveData<Weekday> = MutableLiveData(Weekday.Monday)
    val weekday: LiveData<Weekday>
        get() = _weekday

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val doings: LiveData<List<FullWeekdayDoing>> = Transformations.switchMap(_weekday) {
       weekdayBase.getWeekdayDoings(it.dayId)
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addNewWeekDoing(doing: Doing) {
        viewModelScope.launch {
            val doingId = doingsBase.addDoing(doing)
            val weekdayDoing = WeekdayDoing(
                weekday = _weekday.value!!,
                doingId = doingId,
                position = doings.value!!.size
            )
            weekdayBase.addWeekdayDoing(weekdayDoing)
        }
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            doingsBase.updateDoing(doing)
        }
    }

    fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing) {
        viewModelScope.launch {
            weekdayBase.deleteWeekdayDoing(weekdayDoing)
        }
    }

    fun changeWeekday(weekday: Weekday) {
        _weekday.value = weekday
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}