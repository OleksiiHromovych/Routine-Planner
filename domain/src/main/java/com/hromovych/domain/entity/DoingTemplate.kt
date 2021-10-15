package com.hromovych.domain.entity

import com.hromovych.domain.utils.EqualsCheck
import com.hromovych.domain.utils.Positionable


data class DoingTemplate(
    var id: Long = 0,
    var doing: Doing,
    var templateId: Long,
    override var position: Int = 0,
) : EqualsCheck<DoingTemplate>, Positionable {

    val title: String
        get() = doing.title

    override fun areItemsTheSame(item: DoingTemplate): Boolean {
        return id == item.id
    }

    override fun areContentsTheSame(item: DoingTemplate): Boolean {
        return doing == item.doing &&
                position == item.position
    }

}