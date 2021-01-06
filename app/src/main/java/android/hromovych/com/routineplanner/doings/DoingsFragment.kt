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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class DoingsFragment : DefaultFragment() {

    private var adapter: DoingsAdapter? = null
    private var dateInMillis: Long by Delegates.observable(-1L){
    _, _, _ ->
        updateSubtitle()
    }
    private lateinit var doingLab: DoingLab

    companion object {
        fun newInstance() = DoingsFragment()
    }

    override fun setAdapterToNull() {
        adapter = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doingLab = DoingLab(requireContext())
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
                        doingLab.addNewDailyDoing(dateInMillis, doing)
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
                            dateInMillis,
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
                doingLab.updateDailyDoingInfo(dateInMillis, it)
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
                    if (doingLab.deleteDailyDoing(doing) == 0)
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
        if (dateInMillis == -1L) {
            dateInMillis = Calendar.getInstance().setDayStartTime()
        }

        return doingLab.getDailyDoings(dateInMillis)
    }

    private fun updateSubtitle() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            subtitle = getDateFormatString(dateInMillis)
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
                    dateInMillis
                ) {
                    dateInMillis = it.setDayStartTime()
                    updateUi()
                }
            }
            else -> context.toast(item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDateFormatString(it: Long) =
        SimpleDateFormat.getDateInstance().format(Date(it))

}