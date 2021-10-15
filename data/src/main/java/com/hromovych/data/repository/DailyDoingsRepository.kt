package com.hromovych.data.repository

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.data.embedded.DailyDoingFull
import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.entity.Doing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.daily_doings.DailyDoingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DailyDoingsRepositoryImp(
    private val db: PlannerDatabase,
    private val dailyDoingToPresentationMapper: Mapper<DailyDoingFull, DailyDoing>,
    private val dailyDoingToEntityMapper: Mapper<DailyDoing, com.hromovych.data.entities.DailyDoing>,
    private val doingToPresentationMapper: Mapper<com.hromovych.data.entities.Doing, Doing>
) : DailyDoingsRepository {

    override fun getDailyDoings(date: Int): Flow<List<DailyDoing>> {
        return db.doingsDbDao.getDailyDoingsFull(date).map { list ->
            list.map(dailyDoingToPresentationMapper::convert)
        }
    }

    override suspend fun updateDailyDoings(vararg dailyDoings: DailyDoing) {
        db.doingsDbDao.updateDailyDoing(*dailyDoings.map(dailyDoingToEntityMapper::convert).toTypedArray())
    }

    override suspend fun addDailyDoings(vararg dailyDoings: DailyDoing) {
        db.doingsDbDao.addAllDailyDoing(*dailyDoings.map(dailyDoingToEntityMapper::convert).toTypedArray())
    }

    override suspend fun deleteDailyDoing(dailyDoing: DailyDoing) {
        db.doingsDbDao.deleteDailyDoing(dailyDoingToEntityMapper.convert(dailyDoing))
    }

    override suspend fun getNewDoingsForDay(date: Int): List<Doing> {
        return db.doingsDbDao.getNewDoingsForDay(date).map(doingToPresentationMapper::convert)
    }

}