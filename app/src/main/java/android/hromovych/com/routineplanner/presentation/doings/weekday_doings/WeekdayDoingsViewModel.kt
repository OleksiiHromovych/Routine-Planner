package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.WeekdayDoingsDbDao
import android.hromovych.com.routineplanner.data.mapper.WeekdayDoingToPresentationMapper
import android.hromovych.com.routineplanner.data.utils.Weekday
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.presentation.mappers.DoingToEntityMapper
import android.hromovych.com.routineplanner.presentation.mappers.WeekdayDoingToEntityMapper
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeekdayDoingsViewModel(
    weekdayDoingsDbDao: WeekdayDoingsDbDao,
    doingsDbDao: DoingsDbDao,
) : ViewModel() {

    private val weekdayBase = weekdayDoingsDbDao
    private val doingsBase = doingsDbDao
    private val _weekday: MutableLiveData<Weekday> = MutableLiveData(Weekday.Monday)
    val weekday: LiveData<Weekday>
        get() = _weekday

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val doings: LiveData<List<WeekdayDoing>> = Transformations.switchMap(_weekday) { weekday ->
        weekdayBase.getWeekdayDoings(weekday.dayId).map { list ->
            list.map { WeekdayDoingToPresentationMapper.convert(it) }
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun addNewWeekDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            doingsBase.addDoing(doingEntity).also { doing.id = it }
            val weekdayDoing = WeekdayDoing(
                weekday = _weekday.value!!,
                doing = doing,
                position = doings.value!!.size
            )
            val weekdayDoingEntity = WeekdayDoingToEntityMapper.convert(weekdayDoing)
            weekdayBase.addWeekdayDoing(weekdayDoingEntity)
        }
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            doingsBase.updateDoing(doingEntity)
        }
    }

    fun deleteWeekdayDoing(weekdayDoing: WeekdayDoing) {
        viewModelScope.launch {
            val weekdayDoingEntity = WeekdayDoingToEntityMapper.convert(weekdayDoing)
            weekdayBase.deleteWeekdayDoing(weekdayDoingEntity)
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