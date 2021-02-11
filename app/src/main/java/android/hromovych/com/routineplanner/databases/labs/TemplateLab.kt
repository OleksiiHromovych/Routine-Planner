package android.hromovych.com.routineplanner.databases.labs

import android.content.Context
import android.hromovych.com.routineplanner.databases.DoingsTable
import android.hromovych.com.routineplanner.databases.TemplateDoingsTable
import android.hromovych.com.routineplanner.databases.TemplatesTable
import android.hromovych.com.routineplanner.databases.database
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.templates.Template
import org.jetbrains.anko.db.*
import java.util.*

class TemplateLab(val context: Context) {
    private val db = context.database.writableDatabase

    fun insertNewTemplate(template: Template) =
        db.insert(
            TemplatesTable.TABLE_NAME,
            TemplatesTable.COL_NAME to template.name
        )


    fun getTemplates(): List<Template> {
        val parser = rowParser { id: Long, title: String ->
            Pair(
                id,
                title
            )
        }
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

    fun getTemplate(id: Long): Template = db.select(
        TemplatesTable.TABLE_NAME
    )
        .whereArgs("${TemplatesTable.COL_ID} = {id}", "id" to id)
        .exec {
            parseSingle(rowParser { id: Long, title: String ->
                Template(
                    id = id,
                    name = title
                )
            })
        }.apply {
            doings = getDoings(id)
        }

    fun addNewDoing(template: Template, doing: Doing): Long {
        if (doing.id == -1L)
            doing.id = DoingLab(context).insertNewDoing(doing)
        val id = db.insert(
            TemplateDoingsTable.TABLE_NAME,
            TemplateDoingsTable.COL_TEMPLATE_ID to template.id,
            TemplateDoingsTable.COL_DOING_ID to doing.id,
            TemplateDoingsTable.COL_POSITION to 0,
        )
        return id
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