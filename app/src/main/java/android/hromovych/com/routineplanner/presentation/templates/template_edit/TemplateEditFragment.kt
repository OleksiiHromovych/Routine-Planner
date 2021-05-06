package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.databinding.FragmentTemplateEditBinding
import android.hromovych.com.routineplanner.databinding.ItemTemplateDoingBinding
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.DoingTemplate
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.DialogButton
import android.hromovych.com.routineplanner.presentation.utils.showDecisionDialog
import android.hromovych.com.routineplanner.presentation.utils.showInputDialog
import android.hromovych.com.routineplanner.presentation.utils.showMultiChoiceDoingsDialog

import android.hromovych.com.routineplanner.utils.toast
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect

class TemplateEditFragment : Fragment() {

    private lateinit var viewModel: TemplateEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentTemplateEditBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_template_edit, container, false
        )

        val database = PlannerDatabase.getInstance(requireActivity())
        val arguments = TemplateEditFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = TemplateEditViewModelFactory(arguments.templateId, database.templatesDbDao, database.doingsDbDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TemplateEditViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = object : BasicAdapter<ItemTemplateDoingBinding, DoingTemplate>() {

            override val itemLayoutId = R.layout.item_template_doing

            override var onClickListener: BasicClickListener<DoingTemplate>? =
                BasicClickListener { view, doing ->
                    onItemClickListener(view, doing)
                }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED){
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

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_template_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_action_use_this -> {

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
        // TODO: якось це заставити робити, требаж перенести в viewModel, ну а інтерфейс з звідсе. Подумавть кароч
        // TODO: ну і воно ж має виключати вже наявні, спробувати через бд запит це організувати.
        val items = listOf<Doing>()
        requireContext().showMultiChoiceDoingsDialog(
            R.string.choice_from_exist,
            items
        ) {

        }
    }

    private fun showAddNewDoingDialog() {
        requireContext().showInputDialog(
            R.string.create_new_doing,
            ""
        ) { result ->
            val doing = Doing(title = result)
            viewModel.addNewTemplateDoing(doing)
            context.toast(getString(R.string.doing_added, result))
        }
    }

    private fun onItemClickListener(view: View, doingTemplate: DoingTemplate) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
        popupMenu.inflate(R.menu.doings_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_action_edit -> {
                    requireContext().showInputDialog(
                        R.string.dialog_title_edit_doing,
                        doingTemplate.title
                    ) { editedDoingTitle ->
                        viewModel.updateDoing(doingTemplate.doing.copy(title = editedDoingTitle))
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