package android.hromovych.com.routineplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doings")
data class Doing(

    @PrimaryKey(autoGenerate = true) val id: Long = -1,
    var title: String = ""
)
