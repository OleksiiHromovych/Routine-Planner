package android.hromovych.com.routineplanner.presentation.doings.tasks

interface CoroutineBasicTask<P> {

    suspend fun start(parameters: P)

}