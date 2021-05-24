package android.hromovych.com.routineplanner.domain.usecase

abstract class CoroutineUseCase<in P, R> {

    suspend operator fun invoke(parameters: P): R {
        return execute(parameters)
    }

    @Throws(java.lang.RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R

}