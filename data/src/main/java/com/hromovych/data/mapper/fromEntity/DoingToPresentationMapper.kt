package com.hromovych.data.mapper.fromEntity

import com.hromovych.domain.entity.Doing
import com.hromovych.domain.mapper.Mapper
import com.hromovych.data.entities.Doing as DoingEntity

object DoingToPresentationMapper : Mapper<DoingEntity, Doing> {

    override fun convert(obj: DoingEntity): Doing {
        return Doing(
            id = obj.id,
            title = obj.title,
            active = obj.active
        )
    }

}