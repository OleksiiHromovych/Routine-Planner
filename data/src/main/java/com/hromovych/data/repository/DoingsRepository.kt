package com.hromovych.data.repository

import com.hromovych.data.database.PlannerDatabase
import com.hromovych.domain.entity.Doing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.domain.repository.doings.DoingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DoingsRepositoryImp(
    private val db: PlannerDatabase,
    private val toEntityMapper: Mapper<Doing, com.hromovych.data.entities.Doing>,
    private val toPresentationMapper: Mapper<com.hromovych.data.entities.Doing, Doing>
) : DoingsRepository {

    override suspend fun addDoing(doing: Doing): Long {
        return db.doingsDbDao.addDoing(toEntityMapper.convert(doing))
    }

    override suspend fun updateDoing(doing: Doing) {
        db.doingsDbDao.updateDoing(toEntityMapper.convert(doing))
    }

    override fun getDoings(): Flow<List<Doing>> {
        return db.doingsDbDao.getDoings().map {
            it.map(toPresentationMapper::convert) }
    }

    override suspend fun getActiveDoings(): List<Doing> {
        return db.doingsDbDao.getActiveDoings().map(toPresentationMapper::convert)
    }

}