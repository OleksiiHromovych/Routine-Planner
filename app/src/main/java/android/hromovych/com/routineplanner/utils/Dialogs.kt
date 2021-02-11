package android.hromovych.com.routineplanner.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.doings.Doing
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class DoingEditDialog(
    context: Context,
    inputTitle: String,
    dialogTitle: Int,
    positiveButtonAction: (String) -> Unit
) :
    AlertDialog.Builder(context) {

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_view, null)
        val titleEditText = view.findViewById<TextInputEditText>(R.id.dialog_input_edit_text)


        setView(view)

        setTitle(dialogTitle)

        titleEditText.setText(inputTitle)
        setPositiveButton(context.getString(R.string.positive_button_title)) { _, _ ->
            positiveButtonAction(titleEditText.text.toString())
        }

        setNegativeButton(R.string.negative_button_title, null)

    }
}

fun showTwoActionDialog(
    context: Context,
    message: String,
    leftButtonAction: DialogButton,
    rightButtonAction: DialogButton
): AlertDialog =
    AlertDialog.Builder(context).apply {
        setTitle(message)
        setPositiveButton(rightButtonAction.first) { _, _ ->
            rightButtonAction.second.invoke()
        }
        setNeutralButton(leftButtonAction.first) { _, _ ->
            leftButtonAction.second.invoke()
        }
    }.show()



// first name, second action for button
typealias DialogButton = Pair<Int, () -> Unit>

fun showDoingsMultiSelectedListDialog(
    context: Context,
    messageTitle: String,
    items: List<Doing>,
    positiveButtonAction: (List<Doing>) -> Unit
) {
    AlertDialog.Builder(context).apply {
        val selectedItems = BooleanArray(items.size)

        setTitle(messageTitle)
        setMultiChoiceItems(
            items.map { it.title }.toTypedArray(),
            null
        ) { _: DialogInterface, position: Int, isChecked: Boolean ->
            selectedItems[position] = isChecked
        }
        setPositiveButton(context.getString(R.string.dialog_button_ok)) { _, _ ->
            positiveButtonAction(items.filterIndexed { index, _ ->
                selectedItems[index]
            })
        }

        setNegativeButton(context.getString(R.string.negative_button_title), null)
    }.show()
}

fun showDatePickerDialog(
    context: Context,
    calendar: Calendar,
    callback: (Calendar) -> Unit
) {
    DatePickerDialog(
        context,
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

fun Context.showSingleChoiceDialog(
    @StringRes title: Int,
    items: Array<String>,
    checkedItem: Int,
    onDone: (dialog: AlertDialog, checkedItem: Int) -> Unit
) {
    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setSingleChoiceItems(items, checkedItem, null)
        .setPositiveButton(R.string.dialog_button_ok, null)
        .setNegativeButton(R.string.negative_button_title, null)
        .show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        onDone(dialog, dialog.listView.checkedItemPosition)
    }
}