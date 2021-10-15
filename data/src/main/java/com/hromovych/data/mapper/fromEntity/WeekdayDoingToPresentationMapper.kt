package com.hromovych.data.mapper.fromEntity

import com.hromovych.data.embedded.FullWeekdayDoing
import com.hromovych.domain.entity.WeekdayDoing
import com.hromovych.domain.mapper.Mapper

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