package com.hromovych.domain.entity

import com.hromovych.domain.utils.EqualsCheck
import com.hromovych.domain.utils.Positionable
import com.hromovych.domain.utils.Weekday

data class WeekdayDoing(
    var id: Long = 0,
    var weekday: Weekday,
    var doing: Doing,
    override var position: Int
) : EqualsCheck<WeekdayDoing>, Positionable {

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