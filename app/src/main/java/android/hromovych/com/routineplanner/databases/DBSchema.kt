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

object DayTable {
    const val TABLE_NAME = "day"

    const val COL_ID = "_id"
    const val COL_DATE = "date"
    const val COL_STATUS = "status"
}

object TemplateDoingsTable {
    const val TABLE_NAME = "template_doings"

    const val COL_TEMPLATE_ID = "template_id"
    const val COL_DOING_ID = "doing_id"
    const val COL_POSITION = "position"
}

object DailyDoingsTable {
    const val TABLE_NAME = "daily_doings"

    const val COL_DAY_ID = "day_id"
    const val COL_DOING_ID = "doing_id"
    const val COL_POSITION = "position"
}