package android.hromovych.com.routineplanner.presentation.mappers

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.Doing as DoingEntity

object DoingToEntityMapper : Mapper<Doing, DoingEntity> {

    override fun convert(obj: Doing): DoingEntity {
        return DoingEntity(
            id = obj.id,
            title = obj.title,
            active = obj.active
        )
    }
}