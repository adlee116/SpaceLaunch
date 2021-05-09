package spaceLaunch.core

import kotlinx.coroutines.*

abstract class EmptyParamsUseCase<out Type> where Type : Any? {
    var enableTesting: Boolean = false
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    abstract suspend fun run(): Result<Type, Exception>

    open operator fun invoke(
        scope: CoroutineScope,
        onResult: (Result<Type, Exception>) -> Unit = {}
    ) {
        scope.launch {
            val job =
                if (enableTesting) withContext(scope.coroutineContext) { run() }
                else withContext(ioDispatcher) { run() }
            withContext(scope.coroutineContext) { onResult(job) }
        }
    }
}