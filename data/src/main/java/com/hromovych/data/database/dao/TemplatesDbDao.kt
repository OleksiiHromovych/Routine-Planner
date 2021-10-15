package com.hromovych.data.database.dao

import androidx.room.*
import com.hromovych.data.embedded.FullDoingTemplate
import com.hromovych.data.embedded.TemplateWithFullDoings
import com.hromovych.data.entities.DoingTemplate
import com.hromovych.data.entities.Template
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplatesDbDao {

    @Transaction
    @Query("SELECT * FROM doings_templates WHERE templateId = :templateId")
    suspend fun getTemplateDoings(templateId: Long): List<FullDoingTemplate>

    @Insert
    suspend fun addTemplate(template: Template): Long

    @Transaction
    @Query("SELECT * FROM templates WHERE id = :templateId")
    fun getTemplateWithFullDoings(templateId: Long): Flow<TemplateWithFullDoings>

    @Transaction
    @Query("SELECT * FROM templates ")
    fun getTemplatesWithFullDoings(): Flow<List<TemplateWithFullDoings>>

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