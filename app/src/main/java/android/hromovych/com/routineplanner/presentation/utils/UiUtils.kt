package android.hromovych.com.routineplanner.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.utils.toCalendar
import android.hromovych.com.routineplanner.data.utils.toDatePattern
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.presentation.basic.BasicItem
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.util.forEach
import kotlinx.android.synthetic.main.dialog_input_view.view.*
import kotlinx.android.synthetic.main.rate_layout.view.*
import java.util.*

fun Context.showInputDialog(
    @StringRes title: Int,
    currentValue: String,
    onResult: (result: String) -> Unit,
) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_input_view, null)
    val edit = view.dialog_input_edit_text

    edit.setText(currentValue)

    AlertDialog.Builder(this)
        .setTitle(title)
        .setView(view)
        .setPositiveButton(R.string.save) { _, _ ->
            onResult(edit.text.toString())
        }
        .setNegativeButton(R.string.cancel, null)
        .show()
}

// first name, second action for button
typealias DialogButton = Pair<Int, () -> Unit>

fun Context.showDecisionDialog(
    @StringRes title: Int,
    leftBtn: DialogButton,
    rightBtn: DialogButton,
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setPositiveButton(rightBtn.first) { _, _ ->
            rightBtn.second.invoke()
        }
        .setNeutralButton(leftBtn.first) { _, _ ->
            leftBtn.second.invoke()
        }
        .show()
}

fun Context.showMultiChoiceDoingsDialog(
    @StringRes title: Int,
    items: List<Doing>,
    positiveButtonAction: (List<Doing>) -> Unit,
) {
    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMultiChoiceItems(
            items.map { it.title }.toTypedArray(),
            null, null
        )
        .setPositiveButton(R.string.dialog_button_ok, null)
        .setNegativeButton(R.string.negative_button_title, null)
        .show()

    dialog.positiveButton.setOnClickListener {
        val checkedItems = dialog.listView.checkedItemPositions
        val result = mutableListOf<Doing>()
        checkedItems.forEach { key, value ->
            if (value) {
                result.add(items[key])
            }
        }
        positiveButtonAction(result)
        dialog.cancel()
    }
}

val AlertDialog.positiveButton: Button
    get() = this.getButton(AlertDialog.BUTTON_POSITIVE)

fun Context.showDatePickerDialog(
    calendar: Calendar,
    callback: (Calendar) -> Unit,
) {
    DatePickerDialog(
        this,
        R.style.DatePickerDialog,
        { _, year, monthOfYear, dayOfMonth ->
            callback(
                Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Context.showDatePickerDialog(
    date: Int,
    onResult: (newDate: Int) -> Unit,
) {
    val calendar = date.toCalendar()
    showDatePickerDialog(calendar) {
        onResult(it.toDatePattern())
    }
}

fun Context.showInfoDialog(
    @StringRes title: Int,
    @StringRes message: Int,
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.dialog_button_ok, null)
        .show()
}

fun AppCompatActivity.showDayNightDialog() {
    val items = listOf(
        BasicItem(getString(R.string.day), AppCompatDelegate.MODE_NIGHT_NO),
        BasicItem(getString(R.string.night), AppCompatDelegate.MODE_NIGHT_YES),
        BasicItem(getString(R.string.auto), AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    )

    val sharedPreferencesHelper = SharedPreferencesHelper(this)
    val checkedPos = items.indexOfFirst { it.value == sharedPreferencesHelper.dayNightMode }
    val dialog = AlertDialog.Builder(this)
        .setTitle(R.string.dayNight_dialog_title)
        .setSingleChoiceItems(items.map { it.title }.toTypedArray(), checkedPos, null)
        .setPositiveButton(R.string.dialog_button_ok, null)
        .setNegativeButton(R.string.cancel, null)
        .show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        val resultMode = items[dialog.listView.checkedItemPosition].value
        if (sharedPreferencesHelper.dayNightMode != resultMode) {
            sharedPreferencesHelper.dayNightMode = resultMode
            delegate.localNightMode = resultMode
        }
        dialog.dismiss()
    }
}

fun Context.showThemeDialog(
    onChanged: () -> Unit
) {
    val items = listOf(
        BasicItem(getString(R.string.standard), R.style.Theme_RoutinePlanner_Standard),
        BasicItem(getString(R.string.vasyl), R.style.Theme_RoutinePlanner_Vasyl),
    )

    val sharedPreferencesHelper = SharedPreferencesHelper(this)
    val checkedPos = items.indexOfFirst { it.value == sharedPreferencesHelper.themeId }
    val dialog = AlertDialog.Builder(this)
        .setTitle(R.string.theme_dialog_title)
        .setSingleChoiceItems(items.map { it.title }.toTypedArray(), checkedPos, null)
        .setPositiveButton(R.string.dialog_button_ok, null)
        .setNegativeButton(R.string.cancel, null)
        .show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        val resultMode = items[dialog.listView.checkedItemPosition].value
        if (sharedPreferencesHelper.themeId != resultMode) {
            sharedPreferencesHelper.themeId = resultMode
            onChanged()
        }
        dialog.dismiss()
    }
}

fun Context.showAppRateDialog() {
    val view = LayoutInflater.from(this).inflate(R.layout.rate_layout, null)
    view.message.setText(R.string.rate_app_message)
    view.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->

        when (rating.toInt()) {
            in 1..4 -> {
                view.extra_info_text.visibility = View.VISIBLE
            }
            5 -> toast("5!!!")
        }
    }

    AlertDialog.Builder(this)
        .setView(view)
        .setNegativeButton(R.string.cancel, null)
        .show()
}
