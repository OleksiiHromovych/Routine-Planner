package com.hromovych.domain.repository.doings

import com.hromovych.domain.entity.Doing
import kotlinx.coroutines.flow.Flow

interface DoingsRepository {

    suspend fun addDoing(doing: Doing): Long

    suspend fun updateDoing(doing: Doing)

    fun getDoings(): Flow<List<Doing>>

    suspend fun getActiveDoings(): List<Doing>
}