package com.hromovych.data.mapper.toEntity

import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.DailyDoing as DailyDoingEntity

object DailyDoingToEntityMapper : Mapper<DailyDoing, DailyDoingEntity> {

    override fun convert(obj: DailyDoing): DailyDoingEntity {
        return DailyDoingEntity(
            _id = obj.id,
            date = obj.date,
            doingId = obj.doing.id,
            position = obj.position,
            completed = obj.completed
        )
    }

}