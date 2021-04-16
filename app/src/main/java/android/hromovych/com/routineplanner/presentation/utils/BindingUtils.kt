package android.hromovych.com.routineplanner.presentation.utils

import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("onTouch")
fun View.setOnTouch(listener: View.OnTouchListener?) {
    if (listener != null) {
        this.setOnTouchListener(listener)
    }
}

@BindingAdapter("listData")
fun <T> RecyclerView.bindRecyclerView(data: List<T>?){
    if (adapter is BasicAdapter<*, *>) {
        (adapter as BasicAdapter<*, T>).submitList(data)
    }

}