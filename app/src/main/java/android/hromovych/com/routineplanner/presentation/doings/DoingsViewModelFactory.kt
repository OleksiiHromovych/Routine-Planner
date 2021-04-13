package android.hromovych.com.routineplanner.presentation.doings

import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DoingsViewModelFactory(
    private val data: Int,
    private val dataSource: DoingsDbDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DoingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoingsViewModel(data, dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}