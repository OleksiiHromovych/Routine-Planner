package android.hromovych.com.routineplanner.databases

import android.content.Context
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.templates.Template
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

        db.select(DoingsTable.TABLE_NAME).exec {
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

class TemplateLab(val context: Context) {
    private val db = context.database.writableDatabase

    fun insertNewTemplate(template: Template) =
        db.insert(
            TemplatesTable.TABLE_NAME,
            TemplatesTable.COL_NAME to template.name
        )


    fun getTemplates(): List<Template> {
        val parser = rowParser { id: Long, title: String -> Pair(id, title) }
        val templates = mutableListOf<Template>()

        db.select(TemplatesTable.TABLE_NAME).exec {

            parseList(parser).forEach { templateRaw: Pair<Long, String> ->

                templates += with(templateRaw) {
                    Template(
                        id = first,
                        name = second,
                        doings = getDoings(first)
                    )
                }
            }
        }
        return templates
    }

    fun getDoings(template: Template) = getDoings(template.id)

    private fun getDoings(
        templateId: Long
    ): List<Doing> {
        val doings = mutableListOf<Doing>()

        db.select(TemplateDoingsTable.TABLE_NAME)
            .whereArgs(
                "${TemplateDoingsTable.COL_TEMPLATE_ID} = {template_id}",
                "template_id" to templateId
            ).exec {

                parseList(rowParser { template_id: Int, doing_id: Int, position: Int ->
                    Triple(template_id, doing_id, position)
                }).forEach {
                    // now select doings for this template
                    db.select(DoingsTable.TABLE_NAME)
                        .whereArgs(
                            "${DoingsTable.COL_ID} = {doing_id}",
                            "doing_id" to it.second
                        ) //second is doing_id
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

    fun updateTemplateName(template: Template) =
        db.update(
            TemplatesTable.TABLE_NAME,
            TemplatesTable.COL_NAME to template.name,
        )
            .whereArgs("${TemplatesTable.COL_ID} = {id}", "id" to template.id)
            .exec()

    fun getTemplate(id: Long): Template = db.select(TemplatesTable.TABLE_NAME)
        .whereArgs("${TemplatesTable.COL_ID} = {id}", "id" to id)
        .exec {
            parseSingle(rowParser { id: Long, title: String -> Template(id = id, name = title) })
        }.apply {
            doings = getDoings(id)
        }

    fun addNewDoing(template: Template, doing: Doing) {
        if (doing.id == -1L)
            doing.id = DoingLab(context).insertNewDoing(doing)
        db.insert(
            TemplateDoingsTable.TABLE_NAME,
            TemplateDoingsTable.COL_TEMPLATE_ID to template.id,
            TemplateDoingsTable.COL_DOING_ID to doing.id,
            TemplateDoingsTable.COL_POSITION to 0,
        )
    }

    fun addTemplateToDate(template: Template, date: Calendar): Int {
        val lab = DoingLab(context)
        template.doings.forEach {
            lab.addNewDailyDoing(date, it)
        }
        return template.doings.size
    }
    fun deleteDoing(template: Template, doing: Doing) =
        db.delete(
            TemplateDoingsTable.TABLE_NAME, "${TemplateDoingsTable.COL_DOING_ID} = {doing_id} " +
                    "AND ${TemplateDoingsTable.COL_TEMPLATE_ID} = {template_id}",
            "doing_id" to doing.id, "template_id" to template.id
        )

}

//    need smth like 2021.01.09 for correct equals
private fun Calendar.toLongPattern() =
    listOf(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH).joinToString("") {
        val value = this.get(it)
        if (value < 10) "0$value" else value.toString()
    }.toLong()