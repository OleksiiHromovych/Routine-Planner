package android.hromovych.com.routineplanner.presentation.basic

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter

/**
 * Basic adapt recycler view adapter. Used with bindings layouts
 *
 *  [TData] is variable in [TBinding] layout
 *
 *  Classic layout for single list item present TextView with title, and ImageView with icon. Also
 *  present CheckBox for particular lists.
 *
 *
 *
 * @param TBinding the binding type of every single list item
 * @param TData the type of single element
 *
 * @property itemLayoutId the id of layout with [TBinding]
 * @property checkBoxActive the status of using checkBox in single list item
 * @property onClickListener the list item onClickListener
 */
abstract class BasicAdapter<TBinding : ViewDataBinding, TData> :
    ListAdapter<TData, BasicHolder<TBinding>>(BasicDiffCallback<TData>()) {

    abstract val itemLayoutId: Int

    open var checkBoxActive: Boolean = false
    open var onClickListener: BasicClickListener<TData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicHolder<TBinding> {
        return BasicHolder.from(parent, itemLayoutId)
    }

    override fun onBindViewHolder(holder: BasicHolder<TBinding>, position: Int) {
        val doingData = BasicAdapterModel<TData>(
            onClickListener,
            null,
            checkBoxActive
        )
        holder.bind(getItem(position), doingData)
    }
}