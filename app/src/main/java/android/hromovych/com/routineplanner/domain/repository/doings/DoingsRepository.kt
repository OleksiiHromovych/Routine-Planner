package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing

interface DoingsRepository {

    suspend fun addDoing(doing: Doing): Long

    suspend fun updateDoing(doing: Doing)

}