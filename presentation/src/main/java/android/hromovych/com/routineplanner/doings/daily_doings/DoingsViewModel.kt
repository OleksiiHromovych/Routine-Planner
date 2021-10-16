package android.hromovych.com.routineplanner.doings.daily_doings

import android.hromovych.com.routineplanner.tasks.CopyDailyDoingsToDayTask
import android.hromovych.com.routineplanner.utils.getWeekday
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.hromovych.data.utils.toCalendar
import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.entity.Doing
import com.hromovych.domain.repository.daily_doings.DailyDoingsRepository
import com.hromovych.domain.repository.doings.AddDoingUseCase
import com.hromovych.domain.repository.doings.UpdateDoingUseCase
import com.hromovych.domain.repository.weekday_doings.GetWeekdayDoingsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class DoingsViewModel(
    datePattern: Int,
    private val dailyDoingsRepository: DailyDoingsRepository,
    private val addDoingUseCase: AddDoingUseCase,
    private val updateDoingUseCase: UpdateDoingUseCase,
    private val getWeekdayDoingsUseCase: GetWeekdayDoingsUseCase,
    private val copyDailyDoingsToDayTask: CopyDailyDoingsToDayTask,
) : ViewModel() {

    private val _date = MutableLiveData<Int>(datePattern)
    val date: LiveData<Int>
        get() = _date

    val dailyDoings: LiveData<List<DailyDoing>> = date.switchMap { dateValue ->
        dailyDoingsRepository.getDailyDoings(dateValue).asLiveData()
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

    fun updateDailyDoings(vararg dailyDoing: DailyDoing) {
        viewModelScope.launch {
            dailyDoingsRepository.updateDailyDoings(*dailyDoing)
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

    // if day doings not empty
    fun addWeekdayDoingIfNeed(date: Int) {
        val weekday = date.toCalendar().getWeekday()

        viewModelScope.launch {

            dailyDoingsRepository.getDailyDoings(date)
                .combine(getWeekdayDoingsUseCase(weekday)) { dailyDoings, weekdayDoings ->
                    if (dailyDoings.isNotEmpty()) return@combine emptyList()

                    val dailyDoingsId = dailyDoings.map { it.doing.id }

                    weekdayDoings.filterNot { it.doing.id in dailyDoingsId }
                        .mapIndexed { index, weekdayDoing ->
                            DailyDoing(
                                date = date,
                                doing = weekdayDoing.doing,
                                position = dailyDoings.size + index
                            )
                        }
                }
                .collectLatest {
                    if (it.isNotEmpty()) {
                        dailyDoingsRepository.addDailyDoings(*it.toTypedArray())
                    }
                }
        }
    }

    fun copyCurrentDoingsToDay(date: Int) {
        val fromDay = this.date.value!!
        viewModelScope.launch {
            copyDailyDoingsToDayTask.start(
                CopyDailyDoingsToDayTask.Param(
                    fromDay,
                    date
                )
            )
        }
    }

    sealed class Event {
        data class ShowToast(@StringRes val text: Int) : Event()
        object OnFabClicked : Event()
    }
}
