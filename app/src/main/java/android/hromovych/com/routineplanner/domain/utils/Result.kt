package android.hromovych.com.routineplanner.domain.utils

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

inline fun <reified T> Result<T>.onSuccess(block: (T) -> Unit) = apply {
    if (this is Result.Success) {
        block(data)
    }
}

inline fun <reified T> Result<T>.onError(block: (Exception) -> Unit) = apply {
    if (this is Result.Error) {
        block(exception)
    }
}