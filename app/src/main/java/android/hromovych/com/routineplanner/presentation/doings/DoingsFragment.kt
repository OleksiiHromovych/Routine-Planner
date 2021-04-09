package android.hromovych.com.routineplanner.presentation.doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databinding.FragmentDoingsBinding
import android.hromovych.com.routineplanner.utils.toast
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

//https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#4

class DoingsFragment : Fragment() {

    private val viewModel: DoingsViewModel by lazy {
        ViewModelProvider(this).get(DoingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDoingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_doings, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_templates_list -> {

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