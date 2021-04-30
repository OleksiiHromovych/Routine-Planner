package android.hromovych.com.routineplanner.presentation.utils

import android.graphics.Paint
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("onTouch")
fun View.setOnTouch(listener: View.OnTouchListener?) {
    if (listener != null) {
        this.setOnTouchListener(listener)
    }
}

@BindingAdapter("listData")
fun <T> RecyclerView.bindRecyclerView(data: List<T>?) {
    if (adapter is BasicAdapter<*, *>) {
        @Suppress("UNCHECKED_CAST")
        (adapter as BasicAdapter<*, T>).updateList(data)
    }

}

@BindingAdapter("showOnlyWhenEmpty")
fun <T> View.showOnlyWhenEmpty(data: List<T>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}


@BindingAdapter("afterTextChanged")
fun EditText.afterTextChanged(onChange: (String) -> Unit) {
    this.addTextChangedListener {
        doAfterTextChanged {
            onChange(it.toString())
        }
    }
}

@BindingAdapter("strike")
fun TextView.setStrike(visible: Boolean) {
    paintFlags = if (visible)
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}