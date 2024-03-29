package android.hromovych.com.routineplanner.basic

import android.view.View

class BasicClickListener<T>(val clickListener: (view: View, item: T) -> Unit) {

    fun onClick(view: View, item: T) = clickListener(view, item)
}