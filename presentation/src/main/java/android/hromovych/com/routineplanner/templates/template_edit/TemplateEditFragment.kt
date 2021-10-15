package android.hromovych.com.routineplanner.templates.template_edit

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.basic.BasicAdapter
import android.hromovych.com.routineplanner.basic.BasicClickListener
import android.hromovych.com.routineplanner.basic.getItemTouchHelper
import android.hromovych.com.routineplanner.databinding.FragmentTemplateEditBinding
import android.hromovych.com.routineplanner.databinding.ItemTemplateDoingBinding
import android.hromovych.com.routineplanner.dialogs.showDoingCreationDialog
import android.hromovych.com.routineplanner.dialogs.showDoingEditDialog
import android.hromovych.com.routineplanner.utils.*
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.hromovych.data.utils.toDatePattern
import com.hromovych.domain.entity.DoingTemplate
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.properties.Delegates

class TemplateEditFragment : Fragment(R.layout.fragment_template_edit) {

    private var templateId by Delegates.notNull<Long>()
    private val viewModel: TemplateEditViewModel by viewModel { parametersOf(templateId) }
    private val binding by viewBinding(FragmentTemplateEditBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        templateId = android.hromovych.com.routineplanner.templates.template_edit.TemplateEditFragmentArgs.fromBundle(
            requireArguments()).templateId
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = object : BasicAdapter<ItemTemplateDoingBinding, DoingTemplate>() {

            override val itemLayoutId = R.layout.item_template_doing

            override var onClickListener: BasicClickListener<DoingTemplate>? =
                BasicClickListener { view, doing ->
                    onItemClickListener(view, doing)
                }

            override var onItemTouchHelper: ItemTouchHelper? = getItemTouchHelper<DoingTemplate> {
                viewModel.updateTemplateDoings(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.eventsFlow.collect {
                when (it) {
                    TemplateEditViewModel.Event.OnFabClicked -> {
                        onFabClicked()
                    }
                    is TemplateEditViewModel.Event.ShowToast -> {
                        context.toast(it.text)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_template_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_action_use_this -> {
                requireContext().showDatePickerDialog(Calendar.getInstance()) { calendar ->
                    viewModel.addTemplateDoingsToDay(calendar.toDatePattern())

                    findNavController().navigate(R.id.action_templateEditFragment_to_doingsFragment,
                        Bundle().apply {
                            putInt("date", calendar.toDatePattern())
                        })
//                    findNavController().navigate(TemplateEditFragmentDirections.actionTemplateEditFragmentToDoingsFragment(calendar.toLongPattern()))
                }
            }

            R.id.menu_action_delete -> {
                viewModel.deleteCurrentTemplate()
                findNavController().popBackStack()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onFabClicked() {
        requireContext().showDecisionDialog(
            R.string.creating_new_doing,
            DialogButton(R.string.yet_exist) {
                showYetExistDoingsDialog()
            },
            DialogButton(R.string.add_new) {
                showAddNewDoingDialog()
            }
        )
    }

    private fun showYetExistDoingsDialog() {
        viewModel.receiveNotUsedDoings { items ->
            requireContext().showMultiChoiceDoingsDialog(
                R.string.choice_from_exist,
                items
            ) {
                if (it.isEmpty()) {
                    return@showMultiChoiceDoingsDialog
                }
                viewModel.addTemplateDoings(it)
            }
        }
    }

    private fun showAddNewDoingDialog() {
        requireContext().showDoingCreationDialog { doing ->
            viewModel.addNewTemplateDoing(doing)
        }
    }

    private fun onItemClickListener(view: View, doingTemplate: DoingTemplate) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
        popupMenu.inflate(R.menu.doings_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_action_edit -> {
                    requireContext().showDoingEditDialog(
                        doingTemplate.doing
                    ) { doing ->
                        viewModel.updateDoing(doing)
                    }
                    true
                }
                R.id.menu_action_delete -> {
                    viewModel.deleteTemplateDoing(doingTemplate)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}