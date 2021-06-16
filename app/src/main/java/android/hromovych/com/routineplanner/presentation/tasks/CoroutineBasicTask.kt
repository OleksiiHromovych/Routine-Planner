package android.hromovych.com.routineplanner.presentation.tasks

interface CoroutineBasicTask<P> {

    suspend fun start(parameters: P)

}