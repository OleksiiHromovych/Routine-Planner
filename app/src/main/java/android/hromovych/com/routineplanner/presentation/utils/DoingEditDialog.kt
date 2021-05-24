package android.hromovych.com.routineplanner.presentation.utils

import android.content.Context
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_edit_view.view.*

fun Context.showDoingEditDialog(
    doing: Doing,
    onResult: (result: Doing) -> Unit,
) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_view, null)

    val dialog = AlertDialog.Builder(this)
        .setView(view)
        .show()

    val edit = view.name_field.apply {
        setText(doing.title)
    }
    view.checkBox.isChecked = doing.active
    view.title.setText(R.string.dialog_title_edit_doing)

    view.positive_button.setOnClickListener {
        if (edit.text.toString().isEmpty()) {
            toast(getString(R.string.incorrect_doing_name))
            return@setOnClickListener
        }

        val newDoing = doing.copy(
            title = edit.text.toString(),
            active = view.checkBox.isChecked
        )

        onResult(newDoing)
        dialog.cancel()
    }

    view.negative_button.setOnClickListener {
        dialog.cancel()
    }
}

fun Context.showDoingCreationDialog(
    onResult: (result: Doing) -> Unit
){

    val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_view, null)

    val dialog = AlertDialog.Builder(this)
        .setView(view)
        .show()

    val edit = view.name_field

    view.checkBox.isChecked = true
    view.title.setText(R.string.create_new_doing)

    view.positive_button.setOnClickListener {
        if (edit.text.toString().isEmpty()) {
            toast(getString(R.string.incorrect_doing_name))
            return@setOnClickListener
        }

        val newDoing = Doing(
            title = edit.text.toString(),
            active = view.checkBox.isChecked
        )

        onResult(newDoing)
        dialog.cancel()
    }

    view.negative_button.setOnClickListener {
        dialog.cancel()
    }
}