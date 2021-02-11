package android.hromovych.com.routineplanner.databases

object TemplatesTable {
    const val TABLE_NAME = "templates"

    const val COL_ID = "_id"
    const val COL_NAME = "name"
}

object DoingsTable {
    const val TABLE_NAME = "doings"

    const val COL_ID = "_id"
    const val COL_NAME = "name"
}

object TemplateDoingsTable {
    const val TABLE_NAME = "template_doings"

    const val COL_TEMPLATE_ID = "template_id"
    const val COL_DOING_ID = "doing_id"
    const val COL_POSITION = "position"
}

object DailyDoingsTable {
    const val TABLE_NAME = "daily_doings"

    const val COL_DATE = "day_id"
    const val COL_DOING_ID = "doing_id"
    const val COL_POSITION = "position"
    const val COL_STATUS = "status"
}

object WeekDayDoingsTable {
    const val TABLE_NAME = "week_day_doings"

    const val COL_WEEK_DAY = "week_day"
    const val COL_DOING_ID = "doing_id"
    const val COL_POSITION = "position"
}

