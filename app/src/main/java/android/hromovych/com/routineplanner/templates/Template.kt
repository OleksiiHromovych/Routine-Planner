package android.hromovych.com.routineplanner.templates

import android.hromovych.com.routineplanner.Doing

data class Template(
    var id: Long = -1,
    var name: String = "Empty",
    var doings: List<Doing> = listOf()
)