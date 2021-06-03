package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databinding.FragmentTemplateEditBinding
import android.hromovych.com.routineplanner.databinding.ItemTemplateDoingBinding
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.*
import android.hromovych.com.routineplanner.utils.toast
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class TemplateEditFragment : Fragment(R.layout.fragment_template_edit) {

    private var templateId by Delegates.notNull<Long>()
    private val viewModel: TemplateEditViewModel by viewModel { parametersOf(templateId)}
    private val binding by viewBinding(FragmentTemplateEditBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        templateId = TemplateEditFragmentArgs.fromBundle(requireArguments()).templateId
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        with(binding.toolbar) {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_template_edit)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
        }

        val adapter = object : BasicAdapter<ItemTemplateDoingBinding, DoingTemplate>() {

            override val itemLayoutId = R.layout.item_template_doing

            override var onClickListener: BasicClickListener<DoingTemplate>? =
                BasicClickListener { view, doing ->
                    onItemClickListener(view, doing)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_action_use_this -> {
                //todo this
            }

            R.id.menu_action_delete -> {
                viewModel.deleteCurrentTemplate()
                findNavController().popBackStack()
            }
        }

        return true
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