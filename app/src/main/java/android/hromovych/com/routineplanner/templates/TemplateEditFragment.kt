package android.hromovych.com.routineplanner.templates

import android.graphics.Typeface
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databases.labs.TemplateLab
import android.hromovych.com.routineplanner.defaults.RecyclerWithHeaderFragment
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.doings.DoingsFragment
import android.hromovych.com.routineplanner.utils.showDatePickerDialog
import android.hromovych.com.routineplanner.utils.toDateFormatString
import android.hromovych.com.routineplanner.utils.toast
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class TemplateEditFragment : RecyclerWithHeaderFragment() {

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
        templateLab =
            TemplateLab(
                requireContext()
            )
        template = templateLab.getTemplate(requireArguments().getLong(ARG_TEMPLATE_ID))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)!!

        val textInputLayout =
            inflater.inflate(R.layout.dialog_edit_view, container, false) as TextInputLayout
        headerLayout.addView(textInputLayout)
        setupTextInputLayout(textInputLayout)

        return v

    }

    private fun setupTextInputLayout(textInputLayout: TextInputLayout) {
        textInputLayout.hint = "Template Title"
        textInputLayout.editText?.apply {
            setText(template.name)
            typeface = Typeface.DEFAULT_BOLD
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
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
    }

    override fun getDoings(): List<Doing> = templateLab.getDoings(template).apply {
        template.doings = this
    }

    override fun addNewDoing(doing: Doing): Long = templateLab.addNewDoing(template, doing)

    override fun deleteDoing(doing: Doing): Int = templateLab.deleteDoing(template, doing)

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

            beginTransaction().replace(
                R.id.container,
                DoingsFragment.newInstance(calendar.timeInMillis)
            ).commit()
        }
    }

}