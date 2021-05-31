package android.hromovych.com.routineplanner.domain.di

import android.hromovych.com.routineplanner.domain.repository.doings.AddDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.doings.UpdateDoingUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.AddTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.GetTemplatesWithFullDoingsUseCase
import org.koin.dsl.module

object DomainLayerDI {

    val useCaseModule = module {

        single {
            AddTemplateUseCase(get())
        }

        single {
            GetTemplatesWithFullDoingsUseCase(get())
        }

        single {
            AddDoingUseCase(get())
        }

        single {
            UpdateDoingUseCase(get())
        }
    }
}