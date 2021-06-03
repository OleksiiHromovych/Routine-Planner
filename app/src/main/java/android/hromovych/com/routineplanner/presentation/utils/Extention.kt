package android.hromovych.com.routineplanner.presentation.utils

import android.content.Context
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.domain.utils.Weekday

fun List<DoingTemplate>.toTemplateDoingsString(): String =
    this.joinToString(separator = ";\n") {
        it.title
    }

fun Weekday.getShortName(context: Context): String {
    val stringId = when (this) {
        Weekday.NONE -> R.string.none_abb
        Weekday.Monday -> R.string.monday_abb
        Weekday.Tuesday -> R.string.tuesday_abb
        Weekday.Wednesday -> R.string.wednesday_abb
        Weekday.Thursday -> R.string.thursday_abb
        Weekday.Friday -> R.string.friday_abb
        Weekday.Saturday -> R.string.saturday_abb
        Weekday.Sunday -> R.string.sunday_abb
    }
    return context.getString(stringId)
}