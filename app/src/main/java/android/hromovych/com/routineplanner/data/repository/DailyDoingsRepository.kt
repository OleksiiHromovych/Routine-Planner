package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.daily_doings.DailyDoingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DailyDoingsRepositoryImp(
    private val db: PlannerDatabase,
    private val dailyDoingToPresentationMapper: Mapper<DailyDoingFull, DailyDoing>,
    private val dailyDoingToEntityMapper: Mapper<DailyDoing, android.hromovych.com.routineplanner.data.entities.DailyDoing>,
    private val doingToPresentationMapper: Mapper<android.hromovych.com.routineplanner.data.entities.Doing, Doing>
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