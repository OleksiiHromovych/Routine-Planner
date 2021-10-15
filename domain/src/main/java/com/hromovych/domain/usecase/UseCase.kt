package com.hromovych.domain.usecase

abstract class UseCase<in P, R> {

    operator fun invoke(parameters: P): R {
        return execute(parameters)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R

}