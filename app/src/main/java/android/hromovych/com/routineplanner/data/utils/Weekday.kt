package android.hromovych.com.routineplanner.data.utils

enum class Weekday(val index: Int) {
    NONE(-1)
    ;

    companion object {
        fun getByIndex(index: Int): Weekday {
            val weekday = values().find {
                it.index == index
            }
            return weekday ?: NONE
        }
    }
}