package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class Template(
    @PrimaryKey(autoGenerate = true) var id: Long = -1,
    var name: String = ""
)