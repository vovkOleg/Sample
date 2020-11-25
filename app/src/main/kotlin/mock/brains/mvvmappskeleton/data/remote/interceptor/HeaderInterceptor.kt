package mock.brains.mvvmappskeleton.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class HeaderInterceptor : Interceptor, KoinComponent {

    val repository: InterceptorRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()
        with(newRequestBuilder) {
            header(CONTENT_TYPE, APPLICATION_JSON)
            val token = repository.getToken()
            if (request.headers[HEADER_IGNORE_AUTHORIZATION].isNullOrBlank() && token.isNotBlank()) {
                header(AUTHORIZATION, "$BEARER $token")
            }
        }

        return chain.proceed(newRequestBuilder.build())
    }

    companion object {

        const val HEADER_IGNORE_AUTHORIZATION = "IgnoreAuthorization: true"
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val CONTENT_TYPE = "Content-Type"
        private const val APPLICATION_JSON = "application/json"
    }
}