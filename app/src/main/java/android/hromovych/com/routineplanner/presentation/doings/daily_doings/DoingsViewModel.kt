package android.hromovych.com.routineplanner.presentation.doings.daily_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.mapper.fromEntity.DailyDoingToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.fromEntity.DoingToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DailyDoingToEntityMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DoingToEntityMapper
import android.hromovych.com.routineplanner.data.utils.toCalendar
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class DoingsViewModel(private val datePattern: Int, dataSource: DoingsDbDao) : ViewModel() {

    private val dataBase = dataSource

    private val _date = MutableLiveData<Int>(datePattern)
    val date: LiveData<Int>
        get() = _date

    val dailyDoings: LiveData<List<DailyDoing>> = date.switchMap { dateValue ->
        dataBase.getDailyDoingsFull(dateValue).map { list ->
            list.map { DailyDoingToPresentationMapper.convert(it) }
        }
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val dateString = date.map {
        SimpleDateFormat.getDateInstance().format(it.toCalendar().time)
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            dataBase.updateDoing(doingEntity)
        }
    }

    fun addNewDailyDoing(doing: Doing) {
        viewModelScope.launch {
            val doingEntity = DoingToEntityMapper.convert(doing)
            dataBase.addDoing(doingEntity).also { doing.id = it }
            val dailyDoing = DailyDoing(
                date = date.value!!,
                doing = doing,
                position = dailyDoings.value?.size ?: 0
            )
            val dailyDoingEntity = DailyDoingToEntityMapper.convert(dailyDoing)
            dataBase.addDailyDoing(dailyDoingEntity)
        }
    }

    fun updateDailyDoing(dailyDoing: DailyDoing) {
        viewModelScope.launch {
            val dailyDoingEntity = DailyDoingToEntityMapper.convert(dailyDoing)
            dataBase.updateDailyDoing(dailyDoingEntity)
        }
    }

    fun deleteDailyDoing(dailyDoing: DailyDoing) {
        viewModelScope.launch {
            val dailyDoingEntity = DailyDoingToEntityMapper.convert(dailyDoing)
            val result = dataBase.deleteDailyDoing(dailyDoingEntity)
            if (result == 0) {
                eventChannel.send(Event.ShowToast("Something go wrong. No such id"))
            } else {
//                val deletedTitle = dataBase.getDoingTitle(dailyDoing.doingId)
                eventChannel.send(Event.ShowToast("Item ${dailyDoing.doing.title} deleted"))
            }
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    fun setNewDate(date: Int) {
        _date.value = date
    }

    fun receiveNotUsedDoings(onReceive: (List<Doing>) -> Unit) {
        viewModelScope.launch {
            val newDoings = dataBase.getNewDoingsForDay(date.value!!)
                .map(DoingToPresentationMapper::convert)
            onReceive(newDoings)
        }
    }

    fun addDailyDoings(doings: List<Doing>) {
        viewModelScope.launch {
            val currentListSize = dailyDoings.value?.size ?: 0
            val dailyDoings = doings.mapIndexed { index, doing ->
                val dailyDoing = DailyDoing(
                    date = date.value!!,
                    doing = doing,
                    position = currentListSize + index
                )
                DailyDoingToEntityMapper.convert(dailyDoing)
            }

            dataBase.addAllDailyDoing(*dailyDoings.toTypedArray())
        }
    }

    sealed class Event {
        //        object NavigateToTemplates : Event()
//        object NavigateToWeekdayDoings : Event()
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }
}
