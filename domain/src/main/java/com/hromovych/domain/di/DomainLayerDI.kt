package com.hromovych.domain.di

import com.hromovych.domain.repository.daily_doings.AddDailyDoingsUseCase
import com.hromovych.domain.repository.daily_doings.GetDailyDoingsUseCase
import com.hromovych.domain.repository.doings.AddDoingUseCase
import com.hromovych.domain.repository.doings.GetActiveDoingsUseCase
import com.hromovych.domain.repository.doings.GetDoingsUseCase
import com.hromovych.domain.repository.doings.UpdateDoingUseCase
import com.hromovych.domain.repository.template_edit.AddTemplateDoingsUseCase
import com.hromovych.domain.repository.template_edit.DeleteTemplateDoingUseCase
import com.hromovych.domain.repository.template_edit.GetTemplateWithDoingsUseCase
import com.hromovych.domain.repository.template_edit.UpdateTemplateDoingsUseCase
import com.hromovych.domain.repository.templates.AddTemplateUseCase
import com.hromovych.domain.repository.templates.DeleteTemplateUseCase
import com.hromovych.domain.repository.templates.GetTemplatesWithFullDoingsUseCase
import com.hromovych.domain.repository.templates.UpdateTemplateUseCase
import com.hromovych.domain.repository.weekday_doings.AddWeekdayDoingsUseCase
import com.hromovych.domain.repository.weekday_doings.DeleteWeekdayDoingUseCase
import com.hromovych.domain.repository.weekday_doings.GetWeekdayDoingsUseCase
import com.hromovych.domain.repository.weekday_doings.UpdateWeekdayDoingUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainLayerDI {

    val useCaseModule = module {

        //TemplatesRepository
        single {
            AddTemplateUseCase(get())
        }

        single {
            GetTemplatesWithFullDoingsUseCase(get(), Dispatchers.IO)
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
            GetDoingsUseCase(get(), Dispatchers.IO)
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
            GetTemplateWithDoingsUseCase(get(), Dispatchers.IO)
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

        single {
            GetDailyDoingsUseCase(get(), Dispatchers.IO)
        }
    }
}