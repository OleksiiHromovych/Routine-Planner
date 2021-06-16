package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.doings.DoingsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class DoingsRepositoryImp(
    private val db: PlannerDatabase,
    private val toEntityMapper: Mapper<Doing, android.hromovych.com.routineplanner.data.entities.Doing>,
    private val toPresentationMapper: Mapper<android.hromovych.com.routineplanner.data.entities.Doing, Doing>
) : DoingsRepository {

    override suspend fun addDoing(doing: Doing): Long {
        return db.doingsDbDao.addDoing(toEntityMapper.convert(doing))
    }

    override suspend fun updateDoing(doing: Doing) {
        db.doingsDbDao.updateDoing(toEntityMapper.convert(doing))
    }

    override fun getDoings(): LiveData<List<Doing>> {
        return db.doingsDbDao.getDoings().map {
            it.map(toPresentationMapper::convert) }
    }

    override suspend fun getActiveDoings(): List<Doing> {
        return db.doingsDbDao.getActiveDoings().map(toPresentationMapper::convert)
    }

}