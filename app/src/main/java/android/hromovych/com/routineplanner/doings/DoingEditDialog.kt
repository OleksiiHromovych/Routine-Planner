package android.hromovych.com.routineplanner.doings

import android.app.AlertDialog
import android.content.Context
import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.R
import android.view.LayoutInflater
import com.google.android.material.textfield.TextInputEditText

class DoingEditDialog(
    context: Context,
    doing: Doing,
    title: String,
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