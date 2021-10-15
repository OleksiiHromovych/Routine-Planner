package android.hromovych.com.routineplanner.utils

import com.hromovych.domain.entity.DoingTemplate
import com.hromovych.domain.utils.Positionable

fun List<DoingTemplate>.toTemplateDoingsString(): String =
    this.joinToString(separator = ";\n") {
        it.title
    }

fun List<Positionable>.normalizePositions() {
    this.mutateIndexed { item, index ->
        item.apply {
            position = index
        }
    }
}