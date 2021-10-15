package android.hromovych.com.routineplanner.tasks

interface CoroutineBasicTask<P> {

    suspend fun start(parameters: P)

}