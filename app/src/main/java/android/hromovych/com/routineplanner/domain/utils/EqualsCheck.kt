package android.hromovych.com.routineplanner.domain.utils

interface EqualsCheck<T> {

    fun areItemsTheSame(item: T): Boolean

    fun areContentsTheSame(item: T): Boolean

}