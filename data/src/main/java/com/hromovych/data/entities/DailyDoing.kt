package com.hromovych.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_doings",
    foreignKeys = [ForeignKey(
        entity = Doing::class,
        parentColumns = ["id"],
        childColumns = ["doingId"]
    )]
)
data class DailyDoing(
    @PrimaryKey(autoGenerate = true) var _id: Long = 0,
    var date: Int,
    @ColumnInfo(index = true)
    var doingId: Long,
    var position: Int = 0,
    var completed: Boolean = false

)
