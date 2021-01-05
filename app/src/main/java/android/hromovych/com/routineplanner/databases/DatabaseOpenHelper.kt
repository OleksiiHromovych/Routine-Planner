package android.hromovych.com.routineplanner.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseOpenHelper private constructor(context: Context) :
    ManagedSQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        instance = this
    }

    companion object {
        const val DATABASE_NAME = "RoutinePlannerDatabase"
        const val DATABASE_VERSION = 2

        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) =
            instance ?: DatabaseOpenHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 3 main tables
        db.createTable(
            TemplatesTable.TABLE_NAME, true,
            TemplatesTable.COL_ID to INTEGER + PRIMARY_KEY  + AUTOINCREMENT,
            TemplatesTable.COL_NAME to TEXT
        )
        db.createTable(
            DoingsTable.TABLE_NAME, true,
            DoingsTable.COL_ID to INTEGER + PRIMARY_KEY  + AUTOINCREMENT,
            DoingsTable.COL_NAME to TEXT
        )
        db.createTable(
            DayTable.TABLE_NAME, true,
            DayTable.COL_ID to INTEGER + PRIMARY_KEY  + AUTOINCREMENT,
            DayTable.COL_DATE to INTEGER,   //date to second from unix time start
            DayTable.COL_STATUS to INTEGER,         //0 or 1 as boolean
        )

        // and 2 base for communicate
        db.createTable(
            TemplateDoingsTable.TABLE_NAME, true,
            TemplateDoingsTable.COL_TEMPLATE_ID to INTEGER,
            TemplateDoingsTable.COL_DOING_ID to INTEGER,
            TemplateDoingsTable.COL_POSITION to INTEGER,
        )
        db.createTable(
            DailyDoingsTable.TABLE_NAME, true,
            DailyDoingsTable.COL_POSITION to INTEGER,
            DailyDoingsTable.COL_DAY_ID to INTEGER,
            DailyDoingsTable.COL_DOING_ID to INTEGER,
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(this)