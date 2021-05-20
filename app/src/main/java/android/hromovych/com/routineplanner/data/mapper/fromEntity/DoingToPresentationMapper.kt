package android.hromovych.com.routineplanner.data.mapper.fromEntity

import android.hromovych.com.routineplanner.domain.entity.Doing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.Doing as DoingEntity

object DoingToPresentationMapper : Mapper<DoingEntity, Doing> {

    override fun convert(obj: DoingEntity): Doing {
        return Doing(
            id = obj.id,
            title = obj.title,
            active = obj.active
        )
    }

}