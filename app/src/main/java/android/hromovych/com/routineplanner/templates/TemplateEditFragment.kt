package android.hromovych.com.routineplanner.templates

import android.content.Context
import android.hromovych.com.routineplanner.*
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

class TemplateEditFragment : DefaultFragment() {

    private var adapter: TemplateEditAdapter? = null

    companion object {
        fun newInstance() = TemplateEditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_template_edit, container, false)

        initViews(v)
        v.findViewById<TextView>(R.id.template_edit_input_view).text = "Template Name"

        return v
    }

    override fun setAdapterToNull() {
        adapter = null
    }

    override val onFABClickListener: (View) -> Unit = {
        showTwoActionDialog(
            requireContext(),
            "You want to create new doing or use yet exist?",
            DialogButton(R.string.dialog_button_yet_exist) {
                showMultiSelectedListDialog(
                    requireContext(),
                    "Choice from exist",
                    getDoings().map {
                        it.title
                    }.toTypedArray()
                ) {
                    context.toast(it.toString())
                }
            },
            DialogButton(R.string.dialog_button_new) {
                DoingEditDialog(
                    requireContext(),
                    Doing(""),
                    R.string.dialog_title_create_new_doing
                ) {
                    context.toast("new created")
                }.show()
            }
        )
    }

    override fun updateUi() {
        val doings: List<Doing> = getDoings()
        if (adapter == null) {
            adapter = TemplateEditAdapter(doings) { view: View, doing: Doing ->
                showPopupMenu(requireContext(), view, doing)
            }
            recyclerView.adapter = adapter
        } else {
            adapter!!.doings = doings
            adapter!!.notifyDataSetChanged()
        }

    }

    private fun showPopupMenu(context: Context, view: View, doing: Doing) {
        val popupMenu = PopupMenu(context, view, Gravity.CENTER_HORIZONTAL)
        popupMenu.inflate(R.menu.doings_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_action_edit -> {
                    DoingEditDialog(
                        context,
                        doing,
                        R.string.dialog_title_edit_doing
                    ) { editedDoing ->
                        context.toast(editedDoing.toString())
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    Toast.makeText(context, "Item ${doing.title} deleted", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun getDoings(): List<Doing> {
        val doings = mutableListOf<Doing>()

        doings.add(Doing("test", false))
        doings.add(Doing("This completed", true))

        return doings

    }

}