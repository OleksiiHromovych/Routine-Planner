package android.hromovych.com.routineplanner.domain.entity

import android.hromovych.com.routineplanner.domain.utils.EqualsCheck
import android.hromovych.com.routineplanner.domain.utils.Positionable


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