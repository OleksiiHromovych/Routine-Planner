package android.hromovych.com.routineplanner.databases

import android.content.Context
import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.templates.Template
import org.jetbrains.anko.db.*

class DoingLab(context: Context) {

    private val db = context.database.writableDatabase

    fun insertNewDoing(doing: Doing) =
        db.insert(
            DoingsTable.TABLE_NAME,
            DoingsTable.COL_NAME to doing.title
        )


    fun getDoings(): List<Doing> {
        val parser = rowParser { id: Long, title: String -> Doing(id, title) }
        lateinit var doings: List<Doing>

        db.select(DoingsTable.TABLE_NAME).exec {
            doings = parseList(parser)
        }
        return doings
    }

    fun updateDoing(doing: Doing) =
        db.update(DoingsTable.TABLE_NAME, DoingsTable.COL_NAME to doing.title)
            .whereArgs("${DoingsTable.COL_ID} = {id}", "id" to doing.id)
            .exec()

    fun deleteDoing(doing: Doing) =
        db.delete(
            DoingsTable.TABLE_NAME, "${DoingsTable.COL_ID} = {doing_id}",
            "doing_id" to doing.id
        )
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

                templates += with(templateRaw){
                    Template(id = first,
                    name = second,
                    doings = getDoings(first))
                }}
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
                                    Doing(id, title).apply {
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
    }

    fun addNewDoing(template: Template, doing: Doing) {
        val id = DoingLab(context).insertNewDoing(doing)
        db.insert(
            TemplateDoingsTable.TABLE_NAME,
            TemplateDoingsTable.COL_TEMPLATE_ID to template.id,
            TemplateDoingsTable.COL_DOING_ID to id,
            TemplateDoingsTable.COL_POSITION to 0,
        )
    }

}