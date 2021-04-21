package android.hromovych.com.routineplanner.presentation.basic

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 *  <T> class must have equals method!
 */
class BasicDiffCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}