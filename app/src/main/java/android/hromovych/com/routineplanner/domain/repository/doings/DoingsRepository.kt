package android.hromovych.com.routineplanner.domain.repository.doings

import android.hromovych.com.routineplanner.domain.entity.Doing
import androidx.lifecycle.LiveData

interface DoingsRepository {

    suspend fun addDoing(doing: Doing): Long

    suspend fun updateDoing(doing: Doing)

    fun getDoings(): LiveData<List<Doing>>

    suspend fun getActiveDoings(): List<Doing>
}