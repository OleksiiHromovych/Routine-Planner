package android.hromovych.com.routineplanner

import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

inline var TextView.strike: Boolean
    set(visible) {
        paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG

fun Context.isDarkModeOn(): Boolean =
    (this.resources.configuration.uiMode
            and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun Context?.toast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Long.toDateFormatString() =
    SimpleDateFormat.getDateInstance().format(Date(this))