package android.hromovych.com.routineplanner.data.di

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.data.embedded.FullDoingTemplate
import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.data.embedded.TemplateWithFullDoings
import android.hromovych.com.routineplanner.data.mapper.fromEntity.*
import android.hromovych.com.routineplanner.data.mapper.toEntity.*
import android.hromovych.com.routineplanner.data.repository.*
import android.hromovych.com.routineplanner.domain.entity.*
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.daily_doings.DailyDoingsRepository
import android.hromovych.com.routineplanner.domain.repository.doings.DoingsRepository
import android.hromovych.com.routineplanner.domain.repository.template_edit.TemplateEditRepository
import android.hromovych.com.routineplanner.domain.repository.templates.TemplatesRepository
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.WeekdayDoingsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


object DataLayerDI {

    val mappersModule = module {

        single<Mapper<TemplateWithFullDoings, Template>>(named("templateToPresentation")) {
            TemplateToPresentationMapper(get(named("templateDoingToPresentation")))
        }

        single<Mapper<Template, android.hromovych.com.routineplanner.data.entities.Template>>(named(
            "templateToEntity")) {
            TemplateToEntityMapper
        }

        single<Mapper<Doing, android.hromovych.com.routineplanner.data.entities.Doing>>(named("doingToEntity")) {
            DoingToEntityMapper
        }

        single<Mapper<android.hromovych.com.routineplanner.data.entities.Doing, Doing>>(named("doingToPresentation")) {
            DoingToPresentationMapper
        }

        single<Mapper<DailyDoingFull, DailyDoing>>(named("dailyDoingToPresentation")) {
            DailyDoingToPresentationMapper
        }

        single<Mapper<DailyDoing, android.hromovych.com.routineplanner.data.entities.DailyDoing>>(
            named("dailyDoingToEntity")) {
            DailyDoingToEntityMapper
        }

        single<Mapper<FullDoingTemplate, DoingTemplate>>(named("templateDoingToPresentation")) {
            DoingTemplateToPresentationMapper
        }

        single<Mapper<DoingTemplate, android.hromovych.com.routineplanner.data.entities.DoingTemplate>>(
            named("templateDoingToEntity")) {
            DoingTemplateToEntityMapper
        }

        single<Mapper<FullWeekdayDoing, WeekdayDoing>>(named("weekdayDoingToPresentation")) {
            WeekdayDoingToPresentationMapper
        }

        single<Mapper<WeekdayDoing, android.hromovych.com.routineplanner.data.entities.WeekdayDoing>>(
            named("weekdayDoingToEntity")) {
            WeekdayDoingToEntityMapper
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
                get(named("doingToEntity")),
                get(named("doingToPresentation"))
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

        single<TemplateEditRepository> {
            TemplateEditRepositoryImp(
                get(),
                get(named("templateToPresentation")),
                get(named("templateDoingToEntity"))
            )
        }

        single<WeekdayDoingsRepository> {
            WeekdayDoingsRepositoryIml(
                get(),
                get(named("weekdayDoingToPresentation")),
                get(named("weekdayDoingToEntity"))
            )
        }

    }

    val dataSourceModule = module {

        single {
            PlannerDatabase.getInstance(get())
        }

    }

}