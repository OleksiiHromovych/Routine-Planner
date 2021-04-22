package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.WeekdayDoingsDbDao
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing
import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeekdayDoingsViewModel(
    weekdayDoingsDbDao: WeekdayDoingsDbDao,
    doingsDbDao: DoingsDbDao
) : ViewModel() {

    private val weekdayBase = weekdayDoingsDbDao
    private val doingsBase = doingsDbDao
    private val weekday: MutableLiveData<Weekday> = MutableLiveData(Weekday.firstChecked)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()


    val doings = weekdayBase.getWeekdayDoings(weekday.value!!.dayId)

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addNewWeekDoing(doing: Doing) {
        viewModelScope.launch {
            val doingId = doingsBase.addDoing(doing)
            val weekdayDoing = WeekdayDoing(
                weekday = weekday.value!!,
                doingId = doingId,
                position = doings.value!!.size
            )
            weekdayBase.addWeekdayDoing(weekdayDoing)
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }

}