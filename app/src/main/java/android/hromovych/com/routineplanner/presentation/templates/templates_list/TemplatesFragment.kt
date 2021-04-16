package android.hromovych.com.routineplanner.presentation.templates.templates_list

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databinding.FragmentTemplatesBinding
import android.hromovych.com.routineplanner.presentation.basic.BasicAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class TemplatesFragment: Fragment() {

    private val viewModel by viewModels<TemplatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTemplatesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_templates, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = object : BasicAdapter<>

        return binding.root
    }

}