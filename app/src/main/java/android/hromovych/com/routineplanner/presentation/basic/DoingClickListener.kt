package android.hromovych.com.routineplanner.presentation.basic

import android.view.View

class DoingClickListener<T>(val clickListener: (view: View, doing: T) -> Unit) {

    fun onClick(view: View, doing: T) = clickListener(view, doing)
}