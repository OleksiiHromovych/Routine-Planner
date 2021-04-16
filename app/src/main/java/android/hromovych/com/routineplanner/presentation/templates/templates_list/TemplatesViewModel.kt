package android.hromovych.com.routineplanner.presentation.templates.templates_list

import android.hromovych.com.routineplanner.data.entities.Template
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TemplatesViewModel : ViewModel() {

    private val _templates = MutableLiveData<List<Template>>()
    val templates: LiveData<List<Template>>
        get() = _templates
}