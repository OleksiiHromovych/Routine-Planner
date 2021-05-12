package android.hromovych.com.routineplanner.presentation.doings.daily_doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.utils.toDatePattern
import android.hromovych.com.routineplanner.databinding.FragmentDoingsBinding
import android.hromovych.com.routineplanner.databinding.ItemDoingBinding
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicCheckBoxListener
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.*
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import java.util.*

//https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#4
// https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
// https://habr.com/ru/post/495762/
class DoingsFragment : Fragment(R.layout.fragment_doings) {

    private lateinit var viewModel: DoingsViewModel
    private val binding by viewBinding(FragmentDoingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dataSource = PlannerDatabase.getInstance(requireActivity()).doingsDbDao
        val arguments = DoingsFragmentArgs.fromBundle(requireArguments())
        val date =
            if (arguments.date == -1) Calendar.getInstance().toDatePattern() else arguments.date
        val viewModelFactory = DoingsViewModelFactory(date, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DoingsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        with(binding.toolbar) {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
        }
        val adapter = object : BasicAdapter<ItemDoingBinding, DailyDoing>() {

            override val itemLayoutId: Int = R.layout.item_doing

            override var onClickListener: BasicClickListener<DailyDoing>? =
                BasicClickListener { view, dailyDoing ->
                    onItemClickListener(view, dailyDoing)
                }

            override var checkBoxActive: Boolean = true

            override var onCheckBoxClickListener: BasicCheckBoxListener<DailyDoing>? =
                BasicCheckBoxListener { dailyDoing, checked ->
                    viewModel.updateDailyDoing(dailyDoing.copy(completed = checked))
                }

        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)


        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.eventsFlow.collect {
                when (it) {
                    is DoingsViewModel.Event.ShowToast -> {
                        context.toast(it.text)
                    }
                    DoingsViewModel.Event.OnFabClicked -> {
                        onFabClicked()
                    }
                }
            }
        }
    }

    private val onItemClickListener: (View, DailyDoing) -> Unit =
        { view: View, dailyDoing: DailyDoing ->
            val popupMenu = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
            popupMenu.inflate(R.menu.doings_popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_action_edit -> {
                        requireContext().showInputDialog(
                            R.string.dialog_title_edit_doing,
                            dailyDoing.doing.title,
                        ) { editedDoingTitle ->
                            val doing = dailyDoing.doing.copy(title = editedDoingTitle)
                            viewModel.updateDoing(doing)
                        }
                        true
                    }
                    R.id.menu_action_delete -> {
                        viewModel.deleteDailyDoing(dailyDoing)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_templates_list -> {
                findNavController().navigate(DoingsFragmentDirections.actionDoingsFragmentToTemplatesFragment())
                return true
            }
            R.id.action_date_picker -> {
                requireContext().showDatePickerDialog(
                    viewModel.date.value!!
                ){
                    viewModel.setNewDate(it)
                }
                return true
            }
            R.id.action_weekdays_doings -> {
                findNavController().navigate(DoingsFragmentDirections.actionDoingsFragmentToWeekdayDoingsFragment())
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