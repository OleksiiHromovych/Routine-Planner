package android.hromovych.com.routineplanner.presentation.templates.templates_list

import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TemplatesViewModelFactory(val dataSource: TemplatesDbDao): ViewModelProvider.Factory {

    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TemplatesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TemplatesViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}