package com.hromovych.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "doings_templates",
    foreignKeys = [
        ForeignKey(
            entity = Doing::class,
            parentColumns = ["id"],
            childColumns = ["doingId"]
        ),
        ForeignKey(
            entity = Template::class,
            parentColumns = ["id"],
            childColumns = ["templateId"]
        )
    ]
)
data class DoingTemplate(
    @PrimaryKey(autoGenerate = true) var _id: Long = 0,
    @ColumnInfo(index = true)
    var templateId: Long,
    @ColumnInfo(index = true)
    var doingId: Long,
    var position: Int = 0
)