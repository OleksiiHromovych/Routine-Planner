package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.utils.Weekday
import android.hromovych.com.routineplanner.databinding.FragmentWeekdayDoingsBinding
import android.hromovych.com.routineplanner.databinding.ItemWeekdayDoingBinding
import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.*
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect

class WeekdayDoingsFragment : Fragment() {

    private lateinit var viewModel: WeekdayDoingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWeekdayDoingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weekday_doings, container, false
        )

        val database = PlannerDatabase.getInstance(requireActivity())
        val viewModelFactory =
            WeekdayDoingsViewModelFactory(database.weekdayDoingsDbDao, database.doingsDbDao)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(WeekdayDoingsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        with(binding.toolbar) {
            setupWithNavController(findNavController())
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        val adapter = object : BasicAdapter<ItemWeekdayDoingBinding, WeekdayDoing>() {

            override val itemLayoutId = R.layout.item_weekday_doing

            override var onClickListener: BasicClickListener<WeekdayDoing>? =
                BasicClickListener { view, weekdayDoing ->
                    onItemClickListener(view, weekdayDoing)
                }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        initWeekdaysGroup(binding.weekDaysLayout, inflater)

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.eventsFlow.collect {
                when (it) {
                    is WeekdayDoingsViewModel.Event.ShowToast -> {
                        context.toast(it.text)
                    }
                    WeekdayDoingsViewModel.Event.OnFabClicked -> {
                        onFabClicked()
                    }
                }
            }
        }

        return binding.root
    }

    private fun onItemClickListener(view: View, weekdayDoing: WeekdayDoing) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
        popupMenu.inflate(R.menu.doings_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_action_edit -> {
                    requireContext().showDoingEditDialog(
                        weekdayDoing.doing,
                    ) { doing ->
                        viewModel.updateDoing(doing)
                    }
                    true
                }
                R.id.menu_action_delete -> {
                    viewModel.deleteWeekdayDoing(weekdayDoing)
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
        viewModel.receiveNotUsedDoings { items ->
            requireContext().showMultiChoiceDoingsDialog(
                R.string.choice_from_exist,
                items
            ) {
                if (it.isEmpty()) {
                    return@showMultiChoiceDoingsDialog
                }
                viewModel.addWeekdayDoings(it)
            }
        }
    }

    private fun showAddNewDoingDialog() {
        requireContext().showDoingCreationDialog { doing ->
            viewModel.addNewWeekDoing(doing)
        }
    }

    private fun initWeekdaysGroup(group: RadioGroup, inflater: LayoutInflater) {
        val checkedDay = viewModel.weekday.value
        val days = Weekday.days
        days.forEach { day ->
            val dayView = inflater.inflate(R.layout.weekday_view, group, false) as RadioButton
            dayView.apply {
                id = day.dayId
                text = day.getShortName(context)
                isChecked = day == checkedDay
            }
            group.addView(dayView)
        }
        group.setOnCheckedChangeListener { _, checkedId ->
            viewModel.changeWeekday(Weekday.getById(checkedId))
        }

    }
}