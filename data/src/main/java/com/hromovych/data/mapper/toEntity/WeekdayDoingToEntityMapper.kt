package com.hromovych.data.mapper.toEntity

import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.WeekdayDoing as WeekdayDoingEntity

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