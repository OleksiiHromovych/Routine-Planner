package com.hromovych.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class Template(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = ""
)