package android.hromovych.com.routineplanner.domain.entity

import android.hromovych.com.routineplanner.domain.utils.EqualsCheck
import android.hromovych.com.routineplanner.domain.utils.Weekday

data class WeekdayDoing(
    var id: Long = 0,
    var weekday: Weekday,
    var doing: Doing,
    var position: Int
) : EqualsCheck<WeekdayDoing> {

    val title: String
        get() = doing.title

    override fun areItemsTheSame(item: WeekdayDoing): Boolean {
        return id == item.id
    }

    override fun areContentsTheSame(item: WeekdayDoing): Boolean {
        return weekday == item.weekday &&
                position == item.position &&
                doing == item.doing
    }
}