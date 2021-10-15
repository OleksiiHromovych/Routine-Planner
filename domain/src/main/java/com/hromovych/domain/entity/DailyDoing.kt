package com.hromovych.domain.entity

import com.hromovych.domain.utils.EqualsCheck
import com.hromovych.domain.utils.Positionable

data class DailyDoing(
    var id: Long = 0,
    var date: Int,
    var doing: Doing,
    override var position: Int = 0,
    var completed: Boolean = false
): EqualsCheck<DailyDoing>, Positionable {
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