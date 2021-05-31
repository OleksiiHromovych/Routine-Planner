package android.hromovych.com.routineplanner.presentation.doings.daily_doings

import android.hromovych.com.routineplanner.data.utils.toCalendar
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.repository.daily_doings.DailyDoingsRepository
import android.hromovych.com.routineplanner.domain.repository.doings.AddDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.UpdateDoingUseCase
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class DoingsViewModel(
    private val datePattern: Int,
    private val dailyDoingsRepository: DailyDoingsRepository,
    private val addDoingUseCase: AddDoingUseCase,
    private val updateDoingUseCase: UpdateDoingUseCase
) : ViewModel() {

    private val _date = MutableLiveData<Int>(datePattern)
    val date: LiveData<Int>
        get() = _date

    val dailyDoings: LiveData<List<DailyDoing>> = date.switchMap { dateValue ->
        dailyDoingsRepository.getDailyDoings(dateValue)
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val dateString = date.map {
        SimpleDateFormat.getDateInstance().format(it.toCalendar().time)
    }

    fun updateDoing(doing: Doing) {
        viewModelScope.launch {
            updateDoingUseCase(doing)
        }
    }

    fun addNewDailyDoing(doing: Doing) {
        viewModelScope.launch {
            addDoingUseCase(doing).also { doing.id = it }
            val dailyDoing = DailyDoing(
                date = date.value!!,
                doing = doing,
                position = dailyDoings.value?.size ?: 0
            )
            dailyDoingsRepository.addDailyDoings(dailyDoing)
        }
    }

    fun updateDailyDoing(dailyDoing: DailyDoing) {
        viewModelScope.launch {
            dailyDoingsRepository.updateDailyDoing(dailyDoing)
        }
    }

    fun deleteDailyDoing(dailyDoing: DailyDoing) {
        viewModelScope.launch {
            dailyDoingsRepository.deleteDailyDoing(dailyDoing)
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
            val newDoings = dailyDoingsRepository.getNewDoingsForDay(date.value!!)
            onReceive(newDoings)
        }
    }

    fun addDailyDoings(doings: List<Doing>) {
        viewModelScope.launch {
            val currentListSize = dailyDoings.value?.size ?: 0
            val dailyDoings = doings.mapIndexed { index, doing ->
                DailyDoing(
                    date = date.value!!,
                    doing = doing,
                    position = currentListSize + index
                )
            }
            dailyDoingsRepository.addDailyDoings(*dailyDoings.toTypedArray())
        }
    }

    sealed class Event {
        //        object NavigateToTemplates : Event()
//        object NavigateToWeekdayDoings : Event()
        data class ShowToast(val text: String) : Event()
        object OnFabClicked : Event()
    }
}
