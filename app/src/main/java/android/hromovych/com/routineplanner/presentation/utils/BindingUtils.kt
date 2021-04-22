package android.hromovych.com.routineplanner.presentation.utils

import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.util.Log
import android.view.View
import android.widget.EditText
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
        (adapter as BasicAdapter<*, T>).submitList(data)
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
            Log.d("TAG", "afterTextChanged: ______ $it")
        }
    }
}