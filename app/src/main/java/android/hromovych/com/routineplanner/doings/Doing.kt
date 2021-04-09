package android.hromovych.com.routineplanner.doings

data class Doing(
    var id: Long = -1,
    var title: String = "",
    var position: Int = 0,
    var isCompleted: Boolean = false
)
