package android.hromovych.com.routineplanner.basic

import android.hromovych.com.routineplanner.BR
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BasicHolder<TBinding : ViewDataBinding>(private val binding: TBinding) :
    RecyclerView.ViewHolder(binding.root) {

    open fun <TData> bind(item: TData, itemData: BasicAdapterModel<TData>) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.itemData, itemData)
        binding.executePendingBindings()
    }

    companion object {
        fun <TBinding : ViewDataBinding> from(
            parent: ViewGroup,
            @LayoutRes layoutId: Int
        ): BasicHolder<TBinding> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: TBinding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
            return BasicHolder(binding)
        }
    }
}