package android.hromovych.com.routineplanner.data.mapper.fromEntity

import android.hromovych.com.routineplanner.data.embedded.FullWeekdayDoing
import android.hromovych.com.routineplanner.domain.entity.WeekdayDoing
import android.hromovych.com.routineplanner.domain.mapper.Mapper

object WeekdayDoingToPresentationMapper: Mapper<FullWeekdayDoing, WeekdayDoing> {

    override fun convert(obj: FullWeekdayDoing): WeekdayDoing {
        return WeekdayDoing(
            obj.weekdayDoing._id,
            obj.weekdayDoing.weekday,
            DoingToPresentationMapper.convert(obj.doing),
            obj.weekdayDoing.position
        )
    }

}