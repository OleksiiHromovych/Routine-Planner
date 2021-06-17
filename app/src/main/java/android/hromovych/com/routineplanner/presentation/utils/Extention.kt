package android.hromovych.com.routineplanner.presentation.utils

import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.utils.Positionable

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