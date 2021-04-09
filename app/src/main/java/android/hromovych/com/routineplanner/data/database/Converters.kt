package android.hromovych.com.routineplanner.data.database

import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromWeekday(weekday: Weekday): String {
        return weekday.index.toString()
    }

    @TypeConverter
    fun toWeekDay(index: String): Weekday {
        return Weekday.getByIndex(index.toInt())
    }
}