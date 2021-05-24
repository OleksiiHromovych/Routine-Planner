package android.hromovych.com.routineplanner.data.database

import android.hromovych.com.routineplanner.data.utils.Weekday
import androidx.room.TypeConverter

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