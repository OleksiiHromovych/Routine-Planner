package android.hromovych.com.routineplanner.utils

import android.content.Context
import android.content.SharedPreferences
import android.hromovych.com.routineplanner.R

class SharedPreferencesHelper(val context: Context) {
    companion object {
        private const val PREFERENCE_FILE_KEY = "android.hromovych.com.routineplanner"
        const val DAY_NIGHT_KEY = "dayNight"
        const val THEME_ID_KEY = "themeIdKey"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    //2 is AutoMode by default
    var dayNightMode: Int
        get() = sharedPreferences.getInt(DAY_NIGHT_KEY, 2)
        set(value) {
            sharedPreferences.edit().putInt(DAY_NIGHT_KEY, value).apply()
        }

    var themeId: Int
        get() = sharedPreferences.getInt(THEME_ID_KEY, R.style.Theme_RoutinePlanner_Standard)
        set(value) {
            sharedPreferences.edit().putInt(THEME_ID_KEY, value).apply()
        }
}