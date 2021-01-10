package android.hromovych.com.routineplanner.templates

import android.content.Context
import android.hromovych.com.routineplanner.*
import android.hromovych.com.routineplanner.databases.DoingLab
import android.hromovych.com.routineplanner.databases.TemplateLab
import android.hromovych.com.routineplanner.doings.DoingsFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class TemplateEditFragment : DefaultFragment() {

    private var adapter: TemplateEditAdapter? = null
    private lateinit var template: Template
    private lateinit var templateLab: TemplateLab

    companion object {
        private const val ARG_TEMPLATE_ID = "template id"

        fun newInstance(id: Long) = TemplateEditFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_TEMPLATE_ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        templateLab = TemplateLab(requireContext())
        template = templateLab.getTemplate(requireArguments().getLong(ARG_TEMPLATE_ID))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_template_edit, container, false)

        initViews(v)
        v.findViewById<TextInputEditText>(R.id.template_edit_input_view).apply {
            setText(template.name)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    templateLab.updateTemplateName(template.apply {
                        name = s.toString()
                    })
                }

            })
        }
        return v

    }

    override fun setAdapterToNull() {
        adapter = null
    }

    override val onFABClickListener: (View) -> Unit = {
        val idList = getDoings().map { dayDoing ->
            dayDoing.id
        }
        showTwoActionDialog(
            requireContext(),
            "You want to create new doing or use yet exist?",
            DialogButton(R.string.dialog_button_yet_exist) {
                showDoingsMultiSelectedListDialog(
                    requireContext(),
                    getString(R.string.dialog_title_choice_from_exist),
                    DoingLab(requireContext()).getDoings().filterNot {
                        idList.contains(it.id)
                    }
                ) {
                    for (doing in it) {
                        templateLab.addNewDoing(template, doing)
                    }
                    updateUi()
                }
            },
            DialogButton(R.string.dialog_button_new) {
                DoingEditDialog(
                    requireContext(),
                    "",
                    R.string.dialog_title_create_new_doing
                ) {
                    templateLab.addNewDoing(template, Doing().apply {
                        title = it
                    })
                    updateUi()
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
                        doing.title,
                        R.string.dialog_title_edit_doing
                    ) { editedDoingTitle -> //todo: треба запитувати чи юзер хоче змінити глобально чи створити копію тої, тоді потрібно буде видалити дану з записів шаблону і замінити новою
                        DoingLab(requireContext()).updateDoing(doing.apply {
                            title = editedDoingTitle
                        })
                        updateUi()
                    }.show()
                    true
                }
                R.id.menu_action_delete -> {
                    if (templateLab.deleteDoing(template, doing) == 0)
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

    private fun getDoings(): List<Doing> = templateLab.getDoings(template).apply {
        template.doings = this
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_template_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_use_this -> {
                showDatePickerDialog(
                    requireContext(),
                    Calendar.getInstance()
                ) {
                    extendAndStartDoingsFragment(it)
                }
                return true
            }
            else -> context.toast(item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun extendAndStartDoingsFragment(calendar: Calendar) {
        val size = templateLab.addTemplateToDate(template, calendar)
        context.toast("$size doings where added to ${calendar.timeInMillis.toDateFormatString()}")

        activity?.supportFragmentManager?.apply {
            popBackStack(
                null, FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            beginTransaction().replace(R.id.container, DoingsFragment.newInstance(calendar.timeInMillis)).commit()
        }
    }

}