package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TemplateEditViewModelFactory(val templateId: Long, val dataSource: TemplatesDbDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TemplateEditViewModel::class.java)){
            return TemplateEditViewModel(templateId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}