package android.hromovych.com.routineplanner.utils

import android.graphics.Paint
import android.hromovych.com.routineplanner.basic.BasicAdapter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hromovych.domain.utils.EqualsCheck
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@BindingAdapter("onTouch")
fun View.setOnTouch(listener: View.OnTouchListener?) {
    if (listener != null) {
        this.setOnTouchListener(listener)
    }
}

@BindingAdapter("listData")
fun <T : EqualsCheck<T>> RecyclerView.bindRecyclerView(data: List<T>?) {
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


class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T,
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)
