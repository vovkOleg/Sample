package mock.brains.mvvmappskeleton.core.network

import mock.brains.mvvmappskeleton.data.remote.exception.CommonThrowable
import retrofit2.Response

sealed class Result<T> {

    companion object {
        fun <T> create(response: Response<T>): Result<T> = with(response) {
            if (isSuccessful) {
                ResultSuccess(body())
            } else {
                ResultError(CommonThrowable.ApiError(errorBody()?.string() ?: message()))
            }
        }
    }

    data class ResultError<T>(val exception: CommonThrowable) : Result<T>()

    data class ResultSuccess<T>(val body: T?) : Result<T>()
}