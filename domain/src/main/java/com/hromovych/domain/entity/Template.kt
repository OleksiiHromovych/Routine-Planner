package com.hromovych.domain.entity

import com.hromovych.domain.utils.EqualsCheck

data class Template(
    var id: Long = 0,
    var name: String = "",
    var doings: List<DoingTemplate> = emptyList()
): EqualsCheck<Template>{
    override fun areItemsTheSame(item: Template): Boolean {
        return id == item.id
    }

    override fun areContentsTheSame(item: Template): Boolean {
        return name == item.name &&
                doings == item.doings
    }
}