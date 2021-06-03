package android.hromovych.com.routineplanner.data.repository

import android.hromovych.com.routineplanner.data.database.PlannerDatabase
import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.domain.repository.weekday_doings.WeekdayDoingsRepository
import android.hromovych.com.routineplanner.domain.utils.Weekday
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class WeekdayDoingsRepositoryIml(
    private val db: PlannerDatabase,
    private val toPresentationMapper: Mapper<FullWeekdayDoing, WeekdayDoing>,
    private val toEntityMapper: Mapper<WeekdayDoing, android.hromovych.com.routineplanner.data.entities.WeekdayDoing>,
) : WeekdayDoingsRepository {

    override fun getWeekdayDoings(weekday: Weekday): LiveData<List<WeekdayDoing>> {
        return db.weekdayDoingsDbDao.getWeekdayDoings(weekday.dayId)
            .map { it.map(toPresentationMapper::convert) }
    }

    override suspend fun addWeekdayDoings(vararg doings: WeekdayDoing) {
        db.weekdayDoingsDbDao.addAllWeekdayDoings(*doings.map(toEntityMapper::convert)
            .toTypedArray())
    }

    override suspend fun updateWeekdayDoing(doing: WeekdayDoing) {
        db.weekdayDoingsDbDao.updateWeekdayDoing(toEntityMapper.convert(doing))
    }

    override suspend fun deleteWeekdayDoing(doing: WeekdayDoing) {
        db.weekdayDoingsDbDao.deleteWeekdayDoing(toEntityMapper.convert(doing))
    }
}