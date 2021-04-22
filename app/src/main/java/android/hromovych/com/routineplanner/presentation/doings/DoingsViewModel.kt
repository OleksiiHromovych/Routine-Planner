package android.hromovych.com.routineplanner.presentation.doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.entities.DailyDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DoingsViewModel(private val date: Int, dataSource: DoingsDbDao) : ViewModel() {

    private val dataBase = dataSource

    val dailyDoings = dataBase.getDailyDoingsFull(date)


    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

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

    fun addNewDailyDoing(doing: Doing) {
        viewModelScope.launch {
            val doingId = dataBase.addDoing(doing)
            val dailyDoing = DailyDoing(date = date, doingId = doingId)
            dataBase.addDailyDoing(dailyDoing)
//            val dailyDoingFull = DailyDoingFull
//            dataBase.addDailyDoing(doingFull) //TODO: чото з рум relation додаванням
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

    fun onFabClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.OnFabClicked)
        }
    }

    sealed class Event {
        object NavigateToTemplates : Event()
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }
}
