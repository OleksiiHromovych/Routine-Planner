package android.hromovych.com.routineplanner.presentation.basic

import android.annotation.SuppressLint
import android.hromovych.com.routineplanner.domain.utils.EqualsCheck
import androidx.recyclerview.widget.DiffUtil


class BasicDiffCallback<T: EqualsCheck<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}