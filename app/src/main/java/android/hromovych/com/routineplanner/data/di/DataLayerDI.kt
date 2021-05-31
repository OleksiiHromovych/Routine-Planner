package android.hromovych.com.routineplanner.data.di

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.mapper.fromEntity.DailyDoingToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.fromEntity.DoingToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.fromEntity.TemplateToPresentationMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DailyDoingToEntityMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.DoingToEntityMapper
import android.hromovych.com.routineplanner.data.mapper.toEntity.TemplateToEntityMapper
import android.hromovych.com.routineplanner.data.repository.DailyDoingsRepositoryImp
import android.hromovych.com.routineplanner.data.repository.DoingsRepositoryImp
import android.hromovych.com.routineplanner.data.repository.templates.TemplatesRepositoryImpl
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.daily_doings.DailyDoingsRepository
import android.hromovych.com.routineplanner.domain.repository.doings.DoingsRepository
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

        single<Mapper<Doing, android.hromovych.com.routineplanner.data.entities.Doing>>(named("doingToEntity")){
            DoingToEntityMapper
        }

        single<Mapper<android.hromovych.com.routineplanner.data.entities.Doing, Doing>>(named("doingToPresentation")){
            DoingToPresentationMapper
        }

        single<Mapper<DailyDoingFull, DailyDoing>>(named("dailyDoingToPresentation")){
            DailyDoingToPresentationMapper
        }

        single<Mapper<DailyDoing, android.hromovych.com.routineplanner.data.entities.DailyDoing>>(named("dailyDoingToEntity")){
            DailyDoingToEntityMapper
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

        single<DoingsRepository> {
            DoingsRepositoryImp(
                get(),
                get(named("doingToEntity"))
            )
        }

        single<DailyDoingsRepository> {
            DailyDoingsRepositoryImp(
                get(),
                get(named("dailyDoingToPresentation")),
                get(named("dailyDoingToEntity")),
                get(named("doingToPresentation"))
            )
        }

    }

    val dataSourceModule = module {

        single {
            PlannerDatabase.getInstance(get())
        }

    }

}