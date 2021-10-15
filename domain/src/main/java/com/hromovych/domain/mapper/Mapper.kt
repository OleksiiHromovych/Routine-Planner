package com.hromovych.domain.mapper

interface Mapper<in F, out T> {

    fun convert(obj: F): T

}