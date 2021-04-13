package android.hromovych.com.routineplanner.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onTouch")
fun View.setOnTouch(listener: View.OnTouchListener?) {
    if (listener != null) {
        this.setOnTouchListener(listener)
    }
}