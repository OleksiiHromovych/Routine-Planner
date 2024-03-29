package android.hromovych.com.routineplanner.templates.templates_list

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.basic.BasicAdapter
import android.hromovych.com.routineplanner.basic.BasicClickListener
import android.hromovych.com.routineplanner.databinding.FragmentTemplatesBinding
import android.hromovych.com.routineplanner.utils.showInputDialog
import android.hromovych.com.routineplanner.utils.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hromovych.domain.entity.Template
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class TemplatesFragment : Fragment() {

    private val viewModel: TemplatesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTemplatesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_templates, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = object : BasicAdapter<FragmentTemplatesBinding, Template>() {

            override val itemLayoutId: Int = R.layout.item_template

            override var onClickListener: BasicClickListener<Template>? =
                BasicClickListener { _, template ->
                    viewModel.navigateToTemplateEdit(template)
                }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.eventsFlow.collect {
                when (it) {
                    is TemplatesViewModel.Event.NavigateToTemplateEdit -> {
                        findNavController().navigate(
                            android.hromovych.com.routineplanner.templates.templates_list.TemplatesFragmentDirections.actionTemplatesFragmentToTemplateEditFragment(
                                it.templateID
                            )
                        )
                    }
                    is TemplatesViewModel.Event.ShowToast -> {
                        context.toast(it.text)
                    }
                    TemplatesViewModel.Event.OnFabClicked -> {
                        onFabClicked()
                    }
                }
            }
        }

        return binding.root
    }

    private fun onFabClicked() {
        requireContext().showInputDialog(
            R.string.create_new_template,
            ""
        ) {
            if (isValidName(it)) {
                val template = Template(name = it)
                viewModel.addTemplate(template)
            } else {
                context.toast(R.string.invalid_name)
            }
        }
    }

    private fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }
}