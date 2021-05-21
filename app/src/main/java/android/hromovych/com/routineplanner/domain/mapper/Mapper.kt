package android.hromovych.com.routineplanner.domain.mapper

interface Mapper<in F, out T> {

    fun convert(obj: F): T

}