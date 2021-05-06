package android.hromovych.com.routineplanner.data.mapper

import android.hromovych.com.routineplanner.data.embedded.DailyDoingFull
import android.hromovych.com.routineplanner.domain.entity.DailyDoing
import android.hromovych.com.routineplanner.domain.mapper.Mapper

object DailyDoingToPresentationMapper: Mapper<DailyDoingFull, DailyDoing> {
    override fun convert(obj: DailyDoingFull): DailyDoing {
        return DailyDoing(
            obj.dailyDoing._id,
            obj.dailyDoing.date,
            DoingToPresentationMapper.convert(obj.doing),
            obj.dailyDoing.position,
            obj.dailyDoing.completed
        )
    }
}