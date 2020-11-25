package mock.brains.mvvmappskeleton.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mock.brains.mvvmappskeleton.data.remote.exception.CommonThrowable
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
) = flow {
    emit(CallState.Loading())
    try {
        emit(CallState.Success(apiCall.invoke()))
    } catch (throwable: Throwable) {
        Timber.w(throwable, "Api call failure")
        val failure = when (throwable) {
            is SocketException,
            is UnknownHostException,
            is SocketTimeoutException -> CallState.Failure(CommonThrowable.ApiNotResponding(throwable))
            is IOException -> CallState.Failure(CommonThrowable.NetworkError(throwable))
            is HttpException -> {
                val errorResponse = convertErrorBody<String>(throwable)
                CallState.Failure(CommonThrowable.ApiError(errorResponse))
            }
            else -> CallState.Failure(CommonThrowable.ErrorGettingData())
        }
        emit(failure)
    }
}.flowOn(dispatcher)

// TODO change return model for specific server response
private fun <T> convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()
            ?.errorBody()
            ?.string()
    } catch (exception: Exception) {
        null
    }
}