package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.WeekdayDoingsDbDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeekdayDoingsViewModelFactory(
    val weekdayDoingsDbDao: WeekdayDoingsDbDao,
    val doingsDbDao: DoingsDbDao
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeekdayDoingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeekdayDoingsViewModel(weekdayDoingsDbDao, doingsDbDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}