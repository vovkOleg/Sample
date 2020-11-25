package mock.brains.mvvmappskeleton.core.extension

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T? {
    if (isComplete) {
        return exception?.let {
            throw it
        } ?: run {
            if (isCanceled) throw CancellationException("Task $this was cancelled normally.")
            result
        }
    }

    return suspendCancellableCoroutine { continuation ->
        addOnCompleteListener {
            exception?.let {
                continuation.resumeWithException(it)
            } ?: run {
                if (isCanceled) continuation.cancel() else continuation.resume(result)
            }
        }
    }
}