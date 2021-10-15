package com.hromovych.data.database

import androidx.room.TypeConverter
import com.hromovych.domain.utils.Weekday

class Converters {
    @TypeConverter
    fun fromWeekday(weekday: Weekday): String {
        return weekday.dayId.toString()
    }

    @TypeConverter
    fun toWeekDay(id: String): Weekday {
        return Weekday.getById(id.toInt())
    }
}