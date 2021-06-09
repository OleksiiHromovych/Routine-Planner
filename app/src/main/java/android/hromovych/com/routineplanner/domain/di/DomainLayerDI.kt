package android.hromovych.com.routineplanner.domain.di

import android.hromovych.com.routineplanner.domain.repository.daily_doings.AddDailyDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.AddDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.GetActiveDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.GetDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.UpdateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.AddTemplateDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.DeleteTemplateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.GetTemplateWithDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.template_edit.UpdateTemplateDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.AddTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.DeleteTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.GetTemplatesWithFullDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.UpdateTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.AddWeekdayDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.DeleteWeekdayDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.GetWeekdayDoingsUseCase
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.UpdateWeekdayDoingUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainLayerDI {

    val useCaseModule = module {

        //TemplatesRepository
        single {
            AddTemplateUseCase(get())
        }

        single {
            GetTemplatesWithFullDoingsUseCase(get())
        }

        single {
            DeleteTemplateUseCase(get())
        }

        single {
            UpdateTemplateUseCase(get())
        }

        // DoingsRepository
        single {
            AddDoingUseCase(get())
        }

        single {
            UpdateDoingUseCase(get())
        }

        single {
            GetDoingsUseCase(get())
        }

        single {
            GetActiveDoingsUseCase(get())
        }

        // TemplateEditRepository
        single {
            AddTemplateDoingsUseCase(get())
        }

        single {
            DeleteTemplateDoingUseCase(get())
        }

        single {
            GetTemplateWithDoingsUseCase(get())
        }

        single {
            UpdateTemplateDoingsUseCase(get())
        }

        // WeekdayDoings
        single {
            AddWeekdayDoingsUseCase(get())
        }

        single {
            DeleteWeekdayDoingUseCase(get())
        }

        single {
            GetWeekdayDoingsUseCase(get(), Dispatchers.IO)
        }

        single {
            UpdateWeekdayDoingUseCase(get())
        }

        //DailyDoing
        single {
            AddDailyDoingsUseCase(get())
        }
    }
}