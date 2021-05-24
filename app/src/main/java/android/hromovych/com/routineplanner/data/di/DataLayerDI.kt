package android.hromovych.com.routineplanner.data.di

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.mapper.fromEntity.TemplateToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.TemplateToEntityMapper
import android.hromovych.com.routineplanner.data.repository.templates.TemplatesRepositoryImpl
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.templates.TemplatesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


object DataLayerDI {

    val mappersModule = module {

        single<Mapper<TemplateWithFullDoings, Template>>(named("templateToPresentation")) {
            TemplateToPresentationMapper
        }

        single<Mapper<Template, android.hromovych.com.routineplanner.data.entities.Template>>(named("templateToEntity")) {
            TemplateToEntityMapper
        }

    }

    val repositoryModule = module {

        single<TemplatesRepository> {
            TemplatesRepositoryImpl(
                get(),
                get(named("templateToPresentation")),
                get(named("templateToEntity"))
            )
        }

    }

    val dataSourceModule = module {

        single {
            PlannerDatabase.getInstance(get())
        }

    }

}