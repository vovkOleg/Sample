package mock.brains.mvvmappskeleton.data.remote.exception

import mock.brains.mvvmappskeleton.R

sealed class CommonThrowable(
    message: String? = null,
    messageResId: Int? = null,
    val connectionError: Boolean = false,
    cause: Throwable? = null
) : Throwable(message, cause) {

    class ApiError(message: String?) : CommonThrowable(message = message)

    class ApiNotResponding(cause: Throwable? = null) : CommonThrowable(
        messageResId = R.string.error_serverNotResponding,
        connectionError = true
    )

    class NetworkError(cause: Throwable? = null) : CommonThrowable(
        messageResId = R.string.error_networkErrorMessage,
        connectionError = true
    )

    class ErrorGettingData : CommonThrowable(messageResId = R.string.error_errorGettingData)
}