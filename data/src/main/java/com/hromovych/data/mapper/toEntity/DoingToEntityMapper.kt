package com.hromovych.data.mapper.toEntity

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.Doing as DoingEntity

object DoingToEntityMapper : Mapper<Doing, DoingEntity> {

    override fun convert(obj: Doing): DoingEntity {
        return DoingEntity(
            id = obj.id,
            title = obj.title,
            active = obj.active
        )
    }
}