package android.hromovych.com.routineplanner.presentation.basic

import android.view.View

data class BasicAdapterModel<T>(
    var clickListener: BasicClickListener<T>? = null,
    var touchListener: View.OnTouchListener? = null,
    var checkBoxActive: Boolean = false,
    var checkBoxClickListener: BasicCheckBoxListener<T>? = null
)