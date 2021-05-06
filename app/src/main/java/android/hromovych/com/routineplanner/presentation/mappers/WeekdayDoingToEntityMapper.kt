package android.hromovych.com.routineplanner.presentation.mappers

import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.WeekdayDoing as WeekdayDoingEntity

object WeekdayDoingToEntityMapper : Mapper<WeekdayDoing, WeekdayDoingEntity> {

    override fun convert(obj: WeekdayDoing): WeekdayDoingEntity {
        return WeekdayDoingEntity(
            _id = obj.id,
            weekday = obj.weekday,
            doingId = obj.doing.id,
            position = obj.position
        )
    }

}