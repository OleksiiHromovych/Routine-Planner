package android.hromovych.com.routineplanner.doings

import android.app.AlertDialog
import android.content.Context
import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.R
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputLayout

class DoingEditDialog(
    context: Context,
    doing: Doing,
    title: String,
    positiveButtonAction: (Doing) -> Unit
) :
    AlertDialog.Builder(context) {
    private val textInputView = TextInputLayout(context)
    private var titleEditText: EditText = EditText(context)

    init {
        setTitle(title)
        titleEditText.setText(doing.title)
        titleEditText.hint = context.getString(R.string.doing_dialog_title_field_hint)
        setPositiveButton(context.getString(R.string.positive_button_title)) { _, _ ->
            positiveButtonAction(doing.apply { this.title = titleEditText.text.toString() })
        }
        setNegativeButton(context.getString(R.string.negative_button_title), null)
    }

    override fun show(): AlertDialog {
//        editText.layoutParams = textInputView.layoutParams.apply {
//            width = TextInputLayout.Para
//        }
        textInputView.apply {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams = params
            addView(titleEditText, params)
        }
        setView(textInputView)
        return super.show()

    }
}