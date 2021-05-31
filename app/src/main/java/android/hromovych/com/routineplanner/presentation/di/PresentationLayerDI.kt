package android.hromovych.com.routineplanner.presentation.di

import android.hromovych.com.routineplanner.presentation.doings.daily_doings.DoingsViewModel
import android.hromovych.com.routineplanner.presentation.templates.templates_list.TemplatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationLayerDI {

    val viewModelModule = module {
        viewModel {
            TemplatesViewModel(get(), get())
        }

        // use in fragment -- by viewModel{ parametersOf(date) }
        viewModel {
            DoingsViewModel(get(), get(), get(), get())
        }
    }

}