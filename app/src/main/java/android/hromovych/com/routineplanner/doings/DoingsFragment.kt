package android.hromovych.com.routineplanner.doings

import android.content.Context
import android.hromovych.com.routineplanner.*
import android.hromovych.com.routineplanner.databases.DoingLab
import android.hromovych.com.routineplanner.templates.TemplatesFragment
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

class DoingsFragment : DefaultFragment() {

    private var adapter: DoingsAdapter? = null

    companion object {
        fun newInstance() = DoingsFragment()
    }

    override fun setAdapterToNull() {
        adapter = null
    }

    override val onFABClickListener: (View) -> Unit = {
        DoingEditDialog(
            requireContext(), "",
            R.string.dialog_title_create_new_doing
        ) {
            DoingLab(requireContext()).insertNewDoing(Doing().apply {
                title = it
            })
            updateUi()
        }.show()
    }

    override fun updateUi() {
        val doings = getDoings()
        if (adapter == null) {
            adapter = DoingsAdapter(doings) { view: View, doing: Doing ->
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
                        doing.title,
                        R.string.dialog_title_edit_doing
                    ) { editedDoingTitle ->
                        DoingLab(requireContext()).updateDoing(doing.apply {title=editedDoingTitle})
                        updateUi()
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    if (DoingLab(requireContext()).deleteDoing(doing) == 0)
                        context.toast("Something go wrong. No such id")
                    else
                        Toast.makeText(context, "Item ${doing.title} deleted", Toast.LENGTH_SHORT)
                            .show()
                    updateUi()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun getDoings(): List<Doing> = DoingLab(requireContext()).getDoings()


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_templates_list -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(this.id, TemplatesFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            else -> context.toast(item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

}