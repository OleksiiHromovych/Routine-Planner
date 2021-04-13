package android.hromovych.com.routineplanner.presentation.doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.entities.DailyDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DoingsViewModel(private val date: Int, val dataSource: DoingsDbDao) : ViewModel() {

    private val dataBase = dataSource

    private val _dailyDoings = MutableLiveData<List<DailyDoing>>()
    val dailyDoings: LiveData<List<DailyDoing>>
        get() = _dailyDoings


    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        initializeDailyDoings()
    }

    private fun initializeDailyDoings() {
        viewModelScope.launch {
            _dailyDoings.value = dataBase.getDailyDoings(date)
        }
    }

    fun navigateToTemplates() {
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToTemplates)
        }
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            dataBase.updateDoing(doing)
        }
    }

    fun addNew(){
        viewModelScope.launch {
            eventChannel.send(Event.ShowToast("Added new"))
        }
    }

    fun updateDailyDoing(doing: DailyDoing) {
        viewModelScope.launch {
            dataBase.updateDailyDoing(doing)
        }
    }

    fun deleteDailyDoing(doing: DailyDoing) {
        viewModelScope.launch {
            val result = dataBase.deleteDailyDoing(doing)
            if (result == 0) {
                eventChannel.send(Event.ShowToast("Something go wrong. No such id"))
            } else {
                val deletedTitle = dataBase.getDoingTitle(doing.doingId)
                eventChannel.send(Event.ShowToast("Item $deletedTitle deleted"))
            }
        }
    }

    sealed class Event{
        object NavigateToTemplates: Event()
        data class ShowToast(val text: String): Event()
    }
}
