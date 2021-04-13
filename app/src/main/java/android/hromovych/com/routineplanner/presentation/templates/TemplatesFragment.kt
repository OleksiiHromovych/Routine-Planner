package android.hromovych.com.routineplanner.presentation.templates

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databinding.FragmentTemplatesBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class TemplatesFragment: Fragment() {

    private val viewModel: TemplatesViewModel by lazy {
        ViewModelProvider(this).get(TemplatesViewModel::class.java)
    }

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

        return binding.root
    }

}