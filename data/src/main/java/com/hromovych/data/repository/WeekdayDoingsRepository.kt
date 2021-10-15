package com.hromovych.data.repository

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.data.embedded.FullWeekdayDoing
import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.weekday_doings.WeekdayDoingsRepository
import com.hromovych.domain.utils.Weekday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeekdayDoingsRepositoryIml(
    private val db: PlannerDatabase,
    private val toPresentationMapper: Mapper<FullWeekdayDoing, WeekdayDoing>,
    private val toEntityMapper: Mapper<WeekdayDoing, com.hromovych.data.entities.WeekdayDoing>,
) : WeekdayDoingsRepository {

    override fun getWeekdayDoings(weekday: Weekday): Flow<List<WeekdayDoing>> {
        return db.weekdayDoingsDbDao.getWeekdayDoings(weekday.dayId)
            .map { it.map(toPresentationMapper::convert) }
    }

    override suspend fun addWeekdayDoings(vararg doings: WeekdayDoing) {
        db.weekdayDoingsDbDao.addAllWeekdayDoings(*doings.map(toEntityMapper::convert)
            .toTypedArray())
    }

    override suspend fun updateWeekdayDoings(doings: List<WeekdayDoing>) {
        db.weekdayDoingsDbDao.updateWeekdayDoings(doings.map(toEntityMapper::convert))
    }

    override suspend fun deleteWeekdayDoing(doing: WeekdayDoing) {
        db.weekdayDoingsDbDao.deleteWeekdayDoing(toEntityMapper.convert(doing))
    }
}