package android.hromovych.com.routineplanner.data.utils

enum class Weekday(val dayId: Int) {
    NONE(-1),
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7);

    companion object {
        fun getById(dayId: Int): Weekday {
            val weekday = values().find {
                it.dayId == dayId
            }
            return weekday ?: NONE
        }

        val days: List<Weekday>
            get() = listOf(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)
    }
}