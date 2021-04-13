package android.hromovych.com.routineplanner.presentation.basic

import android.view.View

data class BasicDoingsAdapterModel<T>(
    var clickListener: DoingClickListener<T>? = null,
    var touchListener: View.OnTouchListener? = null,
    var checkBoxActive: Boolean = false
)