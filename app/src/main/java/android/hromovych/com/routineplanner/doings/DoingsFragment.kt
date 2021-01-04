package android.hromovych.com.routineplanner.doings

import android.content.Context
import android.hromovych.com.routineplanner.*
import android.hromovych.com.routineplanner.templates.TemplatesFragment
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

class DoingsFragment : DefaultFragment() {

    private var adapter: DoingsAdapter? = null

    companion object {
        fun newInstance() = DoingsFragment()
    }

    private val doings = mutableListOf<Doing>()

    override fun setAdapterToNull() {
        adapter = null
    }

    override val onFABClickListener: (View) -> Unit = {
        DoingEditDialog(
            requireContext(), Doing(""),
            R.string.dialog_title_create_new_doing
        ) {
            doings += it
            updateUi()
        }.show()
    }

    override fun updateUi() {
        if (doings.isEmpty()) getDoings()
        if (adapter == null) {
            adapter = DoingsAdapter(doings) {view: View, doing: Doing ->
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
                        save(editedDoing)
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    Toast.makeText(context, "Item ${doing.title} deleted", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun save(doing: Doing) {
        doings += doing
    }

    fun getDoings(): List<Doing> {
//        val doings = mutableListOf<Doing>()

        doings.add(Doing("test", false))
        doings.add(Doing("This completed", true))

        return doings
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
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