package mock.brains.mvvmappskeleton.worker

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.single
import mock.brains.mvvmappskeleton.core.network.CallState
import mock.brains.mvvmappskeleton.core.network.isLoading
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class PushUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val repository: PushUpdateWorkerRepository by inject()

    override suspend fun doWork(): Result = coroutineScope {
        val result = repository.updatePushToken()
            .filterNot { it.isLoading() }
            .single()
        when (result) {
            is CallState.Failure -> Result.failure()
            else -> Result.success() // 'else' because Loading result filtered
        }
    }

    companion object {

        private const val RETRY_DELAY = 3 * 60L // 3 min

        fun startWorker(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val pushUpdateWorker = OneTimeWorkRequestBuilder<PushUpdateWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, RETRY_DELAY, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueue(pushUpdateWorker)
        }
    }
}