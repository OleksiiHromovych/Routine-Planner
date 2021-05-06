package android.hromovych.com.routineplanner.domain.mapper

interface Mapper<F, T> {

    fun convert(obj: F): T

}