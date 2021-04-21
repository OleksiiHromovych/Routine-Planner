package android.hromovych.com.routineplanner.defaults

import android.content.Context
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databases.labs.DoingLab
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.utils.*
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class RecyclerWithHeaderFragment : Fragment() {
    abstract fun getDoings(): List<Doing>

    abstract fun addNewDoing(doing: Doing): Long
    abstract fun deleteDoing(doing: Doing): Int
    abstract fun updateDoing(doing: Doing): Int

    lateinit var recyclerView: RecyclerView
    var adapter: DefaultDoingRecyclerAdapter? = null

    lateinit var headerLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_twopane, container, false)

        initViews(v)

        return v
    }

    private fun initViews(v: View) {
        headerLayout = v.findViewById(R.id.header_container)
        recyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        v.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            onFABClickListener(view)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    override fun onPause() {
        super.onPause()
        adapter = null
    }

    private fun updateUi() {
        val doings: List<Doing> = getDoings()
        if (adapter == null) {
            adapter = DefaultDoingRecyclerAdapter(
                doings,
                recyclerView,
                updateDoing = {doing ->
                    updateDoing(doing)
                }
            ) { view: View, doing: Doing ->
                showPopupMenu(requireContext(), view, doing)
            }
            recyclerView.adapter = adapter
        } else {
            adapter!!.doings = doings
        }
    }

    private val onFABClickListener: (View) -> Unit = {
        val idList = getDoings().map { dayDoing ->
            dayDoing.id
        }
        showTwoActionDialog(
            requireContext(),
            "You want to create new doing or use yet exist?",
            DialogButton(R.string.yet_exist) {
                showDoingsMultiSelectedListDialog(
                    requireContext(),
                    getString(R.string.choice_from_exist),
                    DoingLab(
                        requireContext()
                    ).getDoings().filterNot {
                        idList.contains(it.id)
                    }
                ) {
                    for (doing in it) {
                        addNewDoing(doing)
                    }
                    updateUi()
                }
            },
            DialogButton(R.string.add_new) {
                DoingEditDialog(
                    requireContext(),
                    "",
                    R.string.create_new_doing
                ) {
                    addNewDoing(Doing()
                        .apply {
                            title = it
                        })
                    updateUi()
                }.show()
            }
        )
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
                    ) { editedDoingTitle -> //todo: треба запитувати чи юзер хоче змінити глобально чи створити копію тої, тоді потрібно буде видалити дану з записів шаблону і замінити новою
                        DoingLab(
                            requireContext()
                        ).updateDoing(doing.apply {
                            title = editedDoingTitle
                        })
                        updateUi()
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    if (deleteDoing(doing) == 0)
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
}