package android.hromovych.com.routineplanner.doings

import android.content.Context
import android.hromovych.com.routineplanner.*
import android.hromovych.com.routineplanner.databases.DoingLab
import android.hromovych.com.routineplanner.templates.TemplatesFragment
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import java.util.*
import kotlin.properties.Delegates

class DoingsFragment : DefaultFragment() {

    private var adapter: DoingsAdapter? = null

    private var date: Calendar by Delegates.observable(Calendar.getInstance()){
    _, _, _ ->
        updateSubtitle()
    }
    private lateinit var doingLab: DoingLab

    companion object {
        private const val ARG_DAY_TIME = "template id"
        fun newInstance() = DoingsFragment()
        fun newInstance(timeInMillis: Long) = DoingsFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_DAY_TIME, timeInMillis)
            }
        }
    }

    override fun setAdapterToNull() {
        adapter = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doingLab = DoingLab(requireContext())
        arguments?.apply {
            getLong(ARG_DAY_TIME, -1).let {
                date = Calendar.getInstance().apply {this.timeInMillis = it}}
        }
        updateSubtitle()
    }

    override val onFABClickListener: (View) -> Unit = {
        showTwoActionDialog(
            requireContext(),
            "You want to create new doing or use yet exist?",
            DialogButton(R.string.dialog_button_yet_exist) {
                showDoingsMultiSelectedListDialog(
                    requireContext(),
                    getString(R.string.dialog_title_choice_from_exist),
                    doingLab.getDoings()
                ) {
                    for (doing in it){
                        doingLab.addNewDailyDoing(date, doing)
                    }
                    updateUi()
                }
            },
            DialogButton(R.string.dialog_button_new) {
                DoingEditDialog(
                    requireContext(), "",
                    R.string.dialog_title_create_new_doing
                ) {
                    Log.d(
                        "TAG",
                        "index of insert doing: " + doingLab.addNewDailyDoing(
                            date,
                            Doing().apply {
                                title = it
                            })
                    )
                    updateUi()
                }.show()
            }
        )
    }

    override fun updateUi() {
        val doings = getDoings()
        if (adapter == null) {
            adapter = DoingsAdapter(doings, { view: View, doing: Doing ->
                showPopupMenu(requireContext(), view, doing)
            }, {
                doingLab.updateDailyDoingInfo(date, it)
            })
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
                        doingLab.updateDoing(doing.apply {
                            title = editedDoingTitle
                        })
                        updateUi()
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    if (doingLab.deleteDailyDoing(date, doing) == 0)
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

    private fun getDoings(): List<Doing> {

        return doingLab.getDailyDoings(date)
    }

    private fun updateSubtitle() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            subtitle = date.timeInMillis.toDateFormatString()
        }
    }


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
            R.id.action_date_picker -> {
                showDatePickerDialog(
                    requireContext(),
                    date
                ) {
                    date = it
                    updateUi()
                }
            }
            else -> context.toast(item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

}