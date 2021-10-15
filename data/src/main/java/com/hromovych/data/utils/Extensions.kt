package com.hromovych.data.utils

import java.util.*

fun Calendar.toDatePattern() =
    listOf(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH).joinToString("") {
        val value = this.get(it)
        if (value < 10) "0$value" else value.toString()
    }.toInt()

fun Int.toCalendar(): Calendar {
    val iterator = this.toString().iterator()
    return Calendar.getInstance().apply {
        val year = iterator.takeNext(4).joinToString("").toInt()
        val month = iterator.takeNext(2).joinToString("").toInt()
        val day = iterator.takeNext(2).joinToString("").toInt()
        set(year, month, day)
    }
}

private fun CharIterator.takeNext(n: Int): List<Char>{
    val result = mutableListOf<Char>()
    var count = 0
    while (count != n){
        result += this.next()
        count++
    }
    return result
}