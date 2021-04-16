package android.hromovych.com.routineplanner.data.database

import android.content.Context
import android.hromovych.com.routineplanner.data.database.dao.DoingsDbDao
import android.hromovych.com.routineplanner.data.database.dao.TemplatesDbDao
import android.hromovych.com.routineplanner.data.database.dao.WeekdayDoingsDbDao
import android.hromovych.com.routineplanner.data.entities.*
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Doing::class, Template::class, DoingTemplate::class, DailyDoing::class, WeekdayDoing::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDatabase : RoomDatabase() {

    //dao
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