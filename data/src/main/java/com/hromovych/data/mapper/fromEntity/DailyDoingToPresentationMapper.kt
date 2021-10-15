package com.hromovych.data.mapper.fromEntity

import com.hromovych.data.embedded.DailyDoingFull
import com.hromovych.domain.entity.DailyDoing
import com.hromovych.domain.mapper.Mapper

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