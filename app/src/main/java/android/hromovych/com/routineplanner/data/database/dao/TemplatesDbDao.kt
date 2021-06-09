package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.FullDoingTemplate
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.entities.DoingTemplate
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TemplatesDbDao {

    @Transaction
    @Query("SELECT * FROM doings_templates WHERE templateId = :templateId")
    suspend fun getTemplateDoings(templateId: Long): List<FullDoingTemplate>

    @Insert
    suspend fun addTemplate(template: Template): Long

    @Transaction
    @Query("SELECT * FROM templates WHERE id = :templateId")
    fun getTemplateWithFullDoings(templateId: Long): LiveData<TemplateWithFullDoings>

    @Transaction
    @Query("SELECT * FROM templates ")
    fun getTemplatesWithFullDoings(): LiveData<List<TemplateWithFullDoings>>

    @Delete
    suspend fun deleteDoingTemplate(doingTemplate: DoingTemplate)

   @Delete
    suspend fun deleteTemplate(template: Template)

    @Update
    suspend fun updateTemplate(template: Template)

    @Insert
    suspend fun addTemplateDoing(templateDoing: DoingTemplate)

    @Insert
    suspend fun addAllTemplateDoings(vararg templateDoing: DoingTemplate)

    @Update
    suspend fun updateDoingsTemplate(templateDoing: List<DoingTemplate>)
//
//    @Query("SELECT * FROM doings WHERE active = 1 AND id NOT IN (SELECT doingId FROM doings_templates WHERE templateId = :templateId)")
//    suspend fun getNewTemplateDoingsForTemplate(templateId: Long) : List<Doing>
}