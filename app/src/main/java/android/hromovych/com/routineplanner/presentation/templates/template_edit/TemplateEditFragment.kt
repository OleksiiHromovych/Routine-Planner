package android.hromovych.com.routineplanner.presentation.templates.template_edit

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.databinding.FragmentTemplateEditBinding
import android.hromovych.com.routineplanner.templates.TemplateEditFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class TemplateEditFragment: Fragment() {

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

        val dataSource = PlannerDatabase.getInstance(requireActivity()).templatesDbDao
        val arguments = TemplateEditFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = TemplateEditViewModelFactory(arguments.templateId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TemplateEditViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        return binding.root

    }

}