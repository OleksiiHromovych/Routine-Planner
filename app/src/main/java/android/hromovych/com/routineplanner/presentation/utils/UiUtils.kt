package android.hromovych.com.routineplanner.presentation.utils

import android.content.Context
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_edit_view.view.*

fun Context.showInputDialog(
    @StringRes title: Int,
    currentValue: String,
    onResult: (result: String) -> Unit
) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_view, null)
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
    rightBtn: DialogButton
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
    positiveButtonAction: (List<Doing>) -> Unit
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
        val checkedItems = dialog.listView.checkedItemIds
        val result = mutableListOf<Doing>()
        checkedItems.forEach { id ->
            result.add(items[id.toInt()])
        }
        positiveButtonAction(result)
    }
}

val AlertDialog.positiveButton: Button
    get() = this.getButton(AlertDialog.BUTTON_POSITIVE)