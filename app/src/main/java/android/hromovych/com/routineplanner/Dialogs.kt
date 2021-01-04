package android.hromovych.com.routineplanner

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.google.android.material.textfield.TextInputEditText

class DoingEditDialog(
    context: Context,
    doing: Doing,
    title: Int,
    positiveButtonAction: (Doing) -> Unit
) :
    AlertDialog.Builder(context) {//, R.style.AlertDialogTheme

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_view, null)
        val titleEditText = view.findViewById<TextInputEditText>(R.id.dialog_input_edit_text)


        setView(view)

        setTitle(title)

        titleEditText.setText(doing.title)
        setPositiveButton(context.getString(R.string.positive_button_title)) { _, _ ->
            positiveButtonAction(doing.apply { this.title = titleEditText.text.toString() })
        }

        setNegativeButton(context.getString(R.string.negative_button_title), null)

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

fun showMultiSelectedListDialog(
    context: Context,
    messageTitle: String,
    items: Array<String>,
    positiveButtonAction: (List<String>) -> Unit
) {
    AlertDialog.Builder(context).apply {
        val selectedItems = BooleanArray(items.size)

        setTitle(messageTitle)
        setMultiChoiceItems(items, null){ _: DialogInterface, position: Int, isChecked: Boolean ->
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