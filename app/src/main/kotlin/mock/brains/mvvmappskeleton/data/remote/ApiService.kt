package mock.brains.mvvmappskeleton.data.remote

import mock.brains.mvvmappskeleton.data.remote.interceptor.HeaderInterceptor.Companion.HEADER_IGNORE_AUTHORIZATION
import mock.brains.mvvmappskeleton.data.remote.request.AuthRequest
import mock.brains.mvvmappskeleton.data.remote.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers(HEADER_IGNORE_AUTHORIZATION)
    @POST("$API_PATH/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @Headers(HEADER_IGNORE_AUTHORIZATION)
    @POST("$API_PATH/register")
    suspend fun registry(@Body request: AuthRequest): AuthResponse

    companion object {

        private const val API_PATH = "/api"
    }
}