package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.doings.DoingsRepository

class DoingsRepositoryImp(
    private val db: PlannerDatabase,
    private val toEntityMapper: Mapper<Doing, android.hromovych.com.routineplanner.data.entities.Doing>
): DoingsRepository {

    override suspend fun addDoing(doing: Doing): Long {
        return db.doingsDbDao.addDoing(toEntityMapper.convert(doing))
    }

    override suspend fun updateDoing(doing: Doing) {
        db.doingsDbDao.updateDoing(toEntityMapper.convert(doing))
    }
}