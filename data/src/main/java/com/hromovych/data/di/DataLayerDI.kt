package com.hromovych.data.di

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.data.embedded.DailyDoingFull
import com.hromovych.data.embedded.FullDoingTemplate
import com.hromovych.data.embedded.FullWeekdayDoing
import com.hromovych.data.embedded.TemplateWithFullDoings
import com.hromovych.data.mapper.fromEntity.*
import com.hromovych.data.mapper.toEntity.*
import com.hromovych.data.repository.*
import com.hromovych.domain.entity.*
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.daily_doings.DailyDoingsRepository
import com.hromovych.domain.repository.doings.DoingsRepository
import com.hromovych.domain.repository.template_edit.TemplateEditRepository
import com.hromovych.domain.repository.templates.TemplatesRepository
import com.hromovych.domain.repository.weekday_doings.WeekdayDoingsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


object DataLayerDI {

    val mappersModule = module {

        single<Mapper<TemplateWithFullDoings, Template>>(named("templateToPresentation")) {
            TemplateToPresentationMapper(get(named("templateDoingToPresentation")))
        }

        single<Mapper<Template, com.hromovych.data.entities.Template>>(named(
            "templateToEntity")) {
            TemplateToEntityMapper
        }

        single<Mapper<Doing, com.hromovych.data.entities.Doing>>(named("doingToEntity")) {
            DoingToEntityMapper
        }

        single<Mapper<com.hromovych.data.entities.Doing, Doing>>(named("doingToPresentation")) {
            DoingToPresentationMapper
        }

        single<Mapper<DailyDoingFull, DailyDoing>>(named("dailyDoingToPresentation")) {
            DailyDoingToPresentationMapper
        }

        single<Mapper<DailyDoing, com.hromovych.data.entities.DailyDoing>>(
            named("dailyDoingToEntity")) {
            DailyDoingToEntityMapper
        }

        single<Mapper<FullDoingTemplate, DoingTemplate>>(named("templateDoingToPresentation")) {
            DoingTemplateToPresentationMapper
        }

        single<Mapper<DoingTemplate, com.hromovych.data.entities.DoingTemplate>>(
            named("templateDoingToEntity")) {
            DoingTemplateToEntityMapper
        }

        single<Mapper<FullWeekdayDoing, WeekdayDoing>>(named("weekdayDoingToPresentation")) {
            WeekdayDoingToPresentationMapper
        }

        single<Mapper<WeekdayDoing, com.hromovych.data.entities.WeekdayDoing>>(
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