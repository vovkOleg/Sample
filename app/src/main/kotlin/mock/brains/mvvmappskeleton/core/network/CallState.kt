package mock.brains.mvvmappskeleton.core.network

sealed class CallState<out T>(
    val data: T? = null,
    val e: Throwable? = null
) {

    class Loading : CallState<Nothing>()

    class Success<out T>(data: T) : CallState<T>(data)

    class Failure(e: Throwable) : CallState<Nothing>(null, e)
}

// Aliases
typealias CallStateEmpty = CallState<Any>

typealias CallStateAuthorized = CallState<Boolean>

// Methods
fun <T> CallState<T>?.isLoading() = this != null && this is CallState.Loading

fun <T> CallState<T>?.isSuccess() = this != null && this is CallState.Success

fun <T> CallState<T>?.isFailure() = this != null && this is CallState.Failure

fun <T> CallState<T>.asFailure() = this as CallState.Failure