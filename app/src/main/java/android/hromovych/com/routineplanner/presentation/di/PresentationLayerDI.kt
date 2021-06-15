package android.hromovych.com.routineplanner.presentation.di

import android.hromovych.com.routineplanner.MainViewModel
import android.hromovych.com.routineplanner.presentation.doings.daily_doings.DoingsViewModel
import android.hromovych.com.routineplanner.presentation.doings.weekday_doings.WeekdayDoingsViewModel
import android.hromovych.com.routineplanner.presentation.tasks.AddTemplateDoingsToDayTask
import android.hromovych.com.routineplanner.presentation.tasks.CopyDailyDoingsToDayTask
import android.hromovych.com.routineplanner.presentation.templates.template_edit.TemplateEditViewModel
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
            DoingsViewModel(get(), get(), get(), get(), get(), get())
        }

        // by viewModel{ parametersOf(templateId) }
        viewModel {
            TemplateEditViewModel(get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get())
        }

        viewModel {
            WeekdayDoingsViewModel(get(), get(), get(), get(), get(), get(), get())
        }

        viewModel {
            MainViewModel()
        }
    }

    val tasks = module {

        single {
            AddTemplateDoingsToDayTask(get(), get(), get())
        }

        single {
            CopyDailyDoingsToDayTask(get(), get())
        }

    }

}