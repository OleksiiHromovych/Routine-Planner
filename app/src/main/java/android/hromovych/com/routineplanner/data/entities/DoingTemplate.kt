package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "doings_templates")
data class DoingTemplate(
    @PrimaryKey(autoGenerate = true) var _id: Long,
    var templateId: Long,
    var doingId: Long,
    var position: Int = 0
)