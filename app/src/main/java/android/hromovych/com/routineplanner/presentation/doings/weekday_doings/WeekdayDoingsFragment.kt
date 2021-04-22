package android.hromovych.com.routineplanner.presentation.doings.weekday_doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.data.entities.Doing
import android.hromovych.com.routineplanner.databinding.FragmentWeekdayDoingsBinding
import android.hromovych.com.routineplanner.databinding.ItemWeekdayDoingBinding
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.hromovych.com.routineplanner.presentation.basic.BasicClickListener
import android.hromovych.com.routineplanner.presentation.utils.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
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

        val adapter = object : BasicAdapter<ItemWeekdayDoingBinding, FullWeekdayDoing>() {

            override val itemLayoutId = R.layout.item_weekday_doing

            override var onClickListener: BasicClickListener<FullWeekdayDoing>? =
                BasicClickListener { view, doing ->
                    onItemClickListener(view, doing)
                }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

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

    private fun onItemClickListener(view: View, doing: FullWeekdayDoing) {

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
            viewModel.addNewWeekDoing(doing)
        }
    }
}