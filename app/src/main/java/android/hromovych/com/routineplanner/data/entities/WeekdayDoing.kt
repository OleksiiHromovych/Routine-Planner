package android.hromovych.com.routineplanner.data.entities

import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "weekday_doing",
    foreignKeys = [ForeignKey(
        entity = Doing::class,
        parentColumns = ["id"],
        childColumns = ["doingId"]
    )]
)
data class WeekdayDoing(
    @PrimaryKey(autoGenerate = true) var _id: Long = 0,
    var weekday: Weekday,
    @ColumnInfo(index = true)
    var doingId: Long,
    var position: Int
)