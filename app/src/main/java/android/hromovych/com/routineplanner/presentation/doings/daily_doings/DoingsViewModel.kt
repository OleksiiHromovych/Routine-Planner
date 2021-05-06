package android.hromovych.com.routineplanner.presentation.doings.daily_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.mapper.DailyDoingToPresentationMapper
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.presentation.mappers.DailyDoingToEntityMapper
import android.hromovych.com.routineplanner.presentation.mappers.DoingToEntityMapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DoingsViewModel(private val date: Int, dataSource: DoingsDbDao) : ViewModel() {

    private val dataBase = dataSource

    val dailyDoings: LiveData<List<DailyDoing>> = dataBase.getDailyDoingsFull(date).map {  list ->
        list.map { DailyDoingToPresentationMapper.convert(it) }
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

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
                date = date,
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

    sealed class Event {
//        object NavigateToTemplates : Event()
//        object NavigateToWeekdayDoings : Event()
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }
}
