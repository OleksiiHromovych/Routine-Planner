package com.hromovych.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hromovych.data.database.dao.DoingsDbDao
import com.hromovych.data.database.dao.TemplatesDbDao
import com.hromovych.data.database.dao.WeekdayDoingsDbDao
import com.hromovych.data.entities.*

@Database(
    entities = [Doing::class, Template::class, DoingTemplate::class, DailyDoing::class, WeekdayDoing::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDatabase : RoomDatabase() {

    abstract val doingsDbDao: DoingsDbDao
    abstract val templatesDbDao: TemplatesDbDao
    abstract val weekdayDoingsDbDao: WeekdayDoingsDbDao

    companion object {
        private var instance: PlannerDatabase? = null

        fun getInstance(context: Context): PlannerDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        PlannerDatabase::class.java,
                        "routine_planner_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}