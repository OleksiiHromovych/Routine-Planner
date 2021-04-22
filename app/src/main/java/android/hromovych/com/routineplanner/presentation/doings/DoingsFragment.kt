package android.hromovych.com.routineplanner.presentation.doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.data.utils.toDatePattern
import android.hromovych.com.routineplanner.databinding.FragmentDoingsBinding
import android.hromovych.com.routineplanner.databinding.ItemDoingBinding
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.showDecisionDialog
import android.hromovych.com.routineplanner.presentation.utils.showInputDialog
import android.hromovych.com.routineplanner.presentation.utils.showMultiChoiceDoingsDialog
import android.hromovych.com.routineplanner.presentation.utils.toast
import android.hromovych.com.routineplanner.utils.DialogButton
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
import java.util.*

//https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#4
// https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
// https://habr.com/ru/post/495762/
class DoingsFragment : Fragment() {

    private lateinit var viewModel: DoingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDoingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_doings, container, false
        )

        val dataSource = PlannerDatabase.getInstance(requireActivity()).doingsDbDao
        val arguments = DoingsFragmentArgs.fromBundle(requireArguments())
        val date = if (arguments.date == -1) Calendar.getInstance().toDatePattern() else arguments.date
        val viewModelFactory = DoingsViewModelFactory(date, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DoingsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = object : BasicAdapter<ItemDoingBinding, DailyDoingFull>() {

            override val itemLayoutId: Int = R.layout.item_doing

            override var onClickListener: BasicClickListener<DailyDoingFull>? =
                BasicClickListener { view, doing ->
                    onItemClickListener(view, doing)
                }

            override var checkBoxActive: Boolean = true

        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.eventsFlow.collect {
                when (it) {
                    DoingsViewModel.Event.NavigateToTemplates -> {
                        findNavController().navigate(DoingsFragmentDirections.actionDoingsFragmentToTemplatesFragment())
                    }

                    is DoingsViewModel.Event.ShowToast -> {
                        context.toast(it.text)
                    }
                    DoingsViewModel.Event.OnFabClicked -> {
                        onFabClicked()
                    }
                }
            }
        }

        return binding.root
    }

    private val onItemClickListener: (View, DailyDoingFull) -> Unit =
        { view: View, dailyDoingFull: DailyDoingFull ->
            val popupMenu = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
            popupMenu.inflate(R.menu.doings_popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_action_edit -> {
                        requireContext().showInputDialog(
                            R.string.dialog_title_edit_doing,
                            dailyDoingFull.doing.title,
                        ) { editedDoingTitle ->
                            viewModel.updateDoing(dailyDoingFull.doing.apply {
                                title = editedDoingTitle
                            })
                        }
                        true
                    }
                    R.id.menu_action_delete -> {
                        viewModel.deleteDailyDoing(dailyDoingFull.dailyDoing)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
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
            viewModel.addNewDailyDoing(doing)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_templates_list -> {
                viewModel.navigateToTemplates()
                return true
            }
            R.id.action_date_picker -> {

                return true
            }
            R.id.action_weekdays_doings -> {

                return true
            }
            R.id.action_dayNight -> {

            }
            R.id.action_theme -> {

            }
            else -> context.toast(item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

}