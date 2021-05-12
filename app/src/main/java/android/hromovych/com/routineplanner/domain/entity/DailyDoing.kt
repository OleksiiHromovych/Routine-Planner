package android.hromovych.com.routineplanner.domain.entity

import android.hromovych.com.routineplanner.domain.utils.EqualsCheck

data class DailyDoing(
    var id: Long = 0,
    var date: Int,
    var doing: Doing,
    var position: Int = 0,
    var completed: Boolean = false
): EqualsCheck<DailyDoing> {
    override fun areItemsTheSame(item: DailyDoing): Boolean {
        return id == item.id
    }

    override fun areContentsTheSame(item: DailyDoing): Boolean {
        return date == item.date &&
                position == item.position &&
                completed == item.completed &&
                doing == item.doing

    }
}