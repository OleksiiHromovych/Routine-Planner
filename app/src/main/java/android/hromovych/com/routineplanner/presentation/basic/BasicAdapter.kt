package android.hromovych.com.routineplanner.presentation.basic

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter

abstract class BasicAdapter<TBinding : ViewDataBinding, TData> :
    ListAdapter<TData, BasicHolder<TBinding>>(DoingsDiffCallback<TData>()) {

    abstract val layoutId: Int

    open var checkBoxActive: Boolean = false
    open var onClickListener: DoingClickListener<TData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicHolder<TBinding> {
        return BasicHolder.from(parent, layoutId)
    }

    override fun onBindViewHolder(holder: BasicHolder<TBinding>, position: Int) {
        val doingData = BasicDoingsAdapterModel<TData>(
            onClickListener,
            null,
            checkBoxActive
        )
        holder.bind(getItem(position), doingData)
    }
}