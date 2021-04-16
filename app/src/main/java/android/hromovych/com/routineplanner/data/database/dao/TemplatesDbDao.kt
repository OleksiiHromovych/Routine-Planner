package android.hromovych.com.routineplanner.data.database.dao

import android.hromovych.com.routineplanner.data.embedded.TemplateDoing
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TemplatesDbDao {

    @Query("SELECT * FROM doings_templates WHERE templateId = :templateId")
    suspend fun getTemplateDoings(templateId: Int): List<TemplateDoing>
}