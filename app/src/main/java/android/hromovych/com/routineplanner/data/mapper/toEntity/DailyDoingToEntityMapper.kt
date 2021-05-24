package android.hromovych.com.routineplanner.data.mapper.toEntity

import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.mapper.Mapper
import android.hromovych.com.routineplanner.data.entities.DailyDoing as DailyDoingEntity

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