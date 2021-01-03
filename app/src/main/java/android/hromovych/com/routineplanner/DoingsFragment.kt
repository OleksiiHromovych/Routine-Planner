package android.hromovych.com.routineplanner

import android.content.Context
import android.hromovych.com.routineplanner.doings.DoingEditDialog
import android.hromovych.com.routineplanner.doings.DoingsAdapter
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DoingsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: DoingsAdapter? = null

    companion object {
        fun newInstance() = DoingsFragment()

    }

    private val doings = mutableListOf<Doing>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_doings, container, false)

        recyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        v.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            DoingEditDialog(
                requireContext(), Doing(""),
                "Create new doing note"
            ) {
                doings += it
            }.show()

        }

        updateUi()

        return v
    }

    fun updateUi() {
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
                        "Edit doing"
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

}