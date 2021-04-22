package android.hromovych.com.routineplanner.presentation.utils

import android.hromovych.com.routineplanner.data.embedded.FullDoingTemplate

fun List<FullDoingTemplate>.toTemplateDoingsString(): String =
    this.joinToString(separator = ";\n") {
        it.doingTitle
    }