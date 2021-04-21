package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.TemplateDoing
import android.hromovych.com.routineplanner.data.entities.Template
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TemplatesDbDao {

    @Transaction
    @Query("SELECT * FROM doings_templates WHERE templateId = :templateId")
    suspend fun getTemplateDoings(templateId: Int): List<TemplateDoing>

    @Insert
    suspend fun addTemplate(template: Template): Long
}