package android.hromovych.com.routineplanner.basic

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hromovych.domain.utils.EqualsCheck


class BasicDiffCallback<T: EqualsCheck<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}