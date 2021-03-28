package android.hromovych.com.routineplanner.databases.labs

import android.content.Context
import android.hromovych.com.routineplanner.databases.DoingsTable
import android.hromovych.com.routineplanner.databases.WeekDayDoingsTable
import android.hromovych.com.routineplanner.databases.database
import android.hromovych.com.routineplanner.doings.Doing
import org.jetbrains.anko.db.*

class WeekDayLab(val context: Context) {
    private val db = context.database.writableDatabase

    fun getDoings(
        dayIndex: Int
    ): List<Doing> {
        val doings = mutableListOf<Doing>()

        db.select(WeekDayDoingsTable.TABLE_NAME)
            .whereArgs(
                "${WeekDayDoingsTable.COL_WEEK_DAY} = {dayIndex}",
                "dayIndex" to dayIndex)
            .orderBy(WeekDayDoingsTable.COL_POSITION)
            .exec {
                parseList(rowParser { weekDay: Int, doing_id: Int, position: Int ->
                    Triple(weekDay, doing_id, position)
                }).forEach {

                    db.select(DoingsTable.TABLE_NAME)
                        .whereArgs(
                            "${DoingsTable.COL_ID} = {doing_id}",
                            "doing_id" to it.second)
                        .exec {
                            doings +=
                                parseSingle(rowParser { id: Long, title: String ->
                                    Doing(
                                        id,
                                        title
                                    ).apply {
                                        position = it.third
                                    }
                                })
                        }
                }
            }
        return doings
    }

    fun addNewDoing(doing: Doing, dayIndex: Int): Long {
        if (doing.id == -1L)
            doing.id = DoingLab(context).insertNewDoing(doing)
        val id = db.insert(
            WeekDayDoingsTable.TABLE_NAME,
            WeekDayDoingsTable.COL_WEEK_DAY to dayIndex,
            WeekDayDoingsTable.COL_DOING_ID to doing.id,
            WeekDayDoingsTable.COL_POSITION to doing.position,
        )
        return id
    }

    fun deleteDoing(doing: Doing, dayIndex: Int) =
        db.delete(
            WeekDayDoingsTable.TABLE_NAME, "${WeekDayDoingsTable.COL_DOING_ID} = {doing_id} " +
                    "AND ${WeekDayDoingsTable.COL_WEEK_DAY} = {weekDay}",
            "doing_id" to doing.id, "weekDay" to dayIndex
        )

    fun updateDoing(doing: Doing, dayIndex: Int): Int =
        db.update(
            WeekDayDoingsTable.TABLE_NAME,
            WeekDayDoingsTable.COL_WEEK_DAY to dayIndex,
            WeekDayDoingsTable.COL_DOING_ID to doing.id,
            WeekDayDoingsTable.COL_POSITION to doing.position
        ).whereArgs(
            "${WeekDayDoingsTable.COL_DOING_ID} = {doing_id} AND ${WeekDayDoingsTable.COL_WEEK_DAY} = {weekDay}",
            "doing_id" to doing.id,
            "weekDay" to dayIndex
        ).exec()
}