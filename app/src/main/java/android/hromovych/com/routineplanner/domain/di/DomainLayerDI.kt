package android.hromovych.com.routineplanner.domain.di

import android.hromovych.com.routineplanner.domain.repository.templates.AddTemplateUseCase
import android.hromovych.com.routineplanner.domain.repository.templates.GetTemplatesWithFullDoingsUseCase
import org.koin.dsl.module

object DomainLayerDI  {

    val useCaseModule = module {

        single {
            AddTemplateUseCase(get())
        }

        single {
            GetTemplatesWithFullDoingsUseCase(get())
        }

    }
}