package android.hromovych.com.routineplanner.data.utils

import java.util.*

fun Calendar.toDatePattern() =
    listOf(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH).joinToString("") {
        val value = this.get(it)
        if (value < 10) "0$value" else value.toString()
    }.toInt()
