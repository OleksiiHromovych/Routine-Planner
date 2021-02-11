package android.hromovych.com.routineplanner.databases.labs

import android.content.Context
import android.hromovych.com.routineplanner.databases.DailyDoingsTable
import android.hromovych.com.routineplanner.databases.DoingsTable
import android.hromovych.com.routineplanner.databases.database
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.utils.toLongPattern
import org.jetbrains.anko.db.*
import java.util.*

class DoingLab(context: Context) {

    private val db = context.database.writableDatabase

    fun insertNewDoing(doing: Doing) =
        db.insert(
            DoingsTable.TABLE_NAME,
            DoingsTable.COL_NAME to doing.title
        )

    fun addNewDailyDoing(date: Calendar, doing: Doing): Long {
        if (doing.id == -1L)
            doing.id = insertNewDoing(doing)
        return db.insert(
            DailyDoingsTable.TABLE_NAME,
            DailyDoingsTable.COL_DATE to date.toLongPattern(),
            DailyDoingsTable.COL_STATUS to if (doing.isCompleted) 1 else 0,
            DailyDoingsTable.COL_POSITION to doing.position,
            DailyDoingsTable.COL_DOING_ID to doing.id,
        )
    }

    fun deleteDailyDoing(date: Calendar, doing: Doing) =
        db.delete(
            DailyDoingsTable.TABLE_NAME, "${DailyDoingsTable.COL_DOING_ID} = {doing_id} " +
                    "AND ${DailyDoingsTable.COL_DATE} = {date}",
            "doing_id" to doing.id, "date" to date.toLongPattern()
        )


    fun getDoings(): List<Doing> {
        val parser = rowParser { id: Long, title: String ->
            Doing(
                id,
                title
            )
        }
        lateinit var doings: List<Doing>

        db.select(DoingsTable.TABLE_NAME).orderBy(
            DoingsTable.COL_NAME
        ).exec {
            doings = parseList(parser)
        }
        return doings
    }

    fun getDoing(id: Long): Doing {
        val parser = rowParser { id: Long, title: String ->
            Doing(
                id,
                title
            )
        }
        var doing: Doing? = null
        db.select(DoingsTable.TABLE_NAME)
            .whereArgs("${DoingsTable.COL_ID} = {id}", "id" to id)
            .exec {
                doing = parseSingle(parser)
            }
        return doing ?: throw Exception("No doing with this id")
    }

    fun updateDoing(doing: Doing) =
        db.update(DoingsTable.TABLE_NAME, DoingsTable.COL_NAME to doing.title)
            .whereArgs("${DoingsTable.COL_ID} = {id}", "id" to doing.id)
            .exec()

    fun updateDailyDoingInfo(date: Calendar, doing: Doing) =
        db.update(
            DailyDoingsTable.TABLE_NAME,
            DailyDoingsTable.COL_DATE to date.toLongPattern(),
            DailyDoingsTable.COL_STATUS to if (doing.isCompleted) 1 else 0,
            DailyDoingsTable.COL_POSITION to doing.position
        )
            .whereArgs("${DailyDoingsTable.COL_DOING_ID} = {id} AND ${DailyDoingsTable.COL_DATE} = {date}", "id" to doing.id, "date" to date.toLongPattern())
            .exec()

    fun deleteDoing(doing: Doing) =
        db.delete(
            DoingsTable.TABLE_NAME, "${DoingsTable.COL_ID} = {doing_id}",
            "doing_id" to doing.id
        )

    fun getDailyDoings(data: Calendar): List<Doing> {
        return db.select(
            DailyDoingsTable.TABLE_NAME,
            DailyDoingsTable.COL_DOING_ID,
            DailyDoingsTable.COL_POSITION,
            DailyDoingsTable.COL_STATUS,
        ).whereArgs(
            "${DailyDoingsTable.COL_DATE} = {data}",
            "data" to data.toLongPattern()
        )
            .exec<List<Doing>> {
                parseList(rowParser { doing_id: Long, position: Int, status: Int ->

                    Doing().apply {
                        this.id = doing_id
                        isCompleted = status == 1
                        this.position = position
                        title = getDoing(doing_id).title
                    }
                })
            }
    }

}