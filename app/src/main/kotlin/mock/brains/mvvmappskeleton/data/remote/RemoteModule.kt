package mock.brains.mvvmappskeleton.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import mock.brains.mvvmappskeleton.BuildConfig
import mock.brains.mvvmappskeleton.data.remote.interceptor.HeaderInterceptor
import mock.brains.mvvmappskeleton.data.remote.interceptor.InterceptorRepository
import mock.brains.mvvmappskeleton.data.remote.serializer.DateTimeSerializer
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

private val IS_DEBUG = BuildConfig.DEBUG
private const val SCHEME = "https"
private const val BASE_URL = "reqres.in" // TODO fill host
private val HTTP_PORT: Int? = null
private const val READ_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 120L
private const val BASE_DATE_TIME_FORMAT = ""

val remoteModule = module {

    fun <T> makeAsyncApiService(
        scheme: String,
        baseUrl: String,
        port: Int?,
        okHttpClient: OkHttpClient,
        gson: Gson,
        serviceClass: Class<T>
    ): T {
        val builder = HttpUrl.Builder()
            .scheme(scheme)
            .host(baseUrl)
        port?.let { builder.port(port) }

        val retrofit = Retrofit.Builder()
            .baseUrl(builder.build())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(serviceClass)
    }

    fun makeHttpClient(
        interceptors: ArrayList<Interceptor>?,
        connectTimeout: Long,
        writeTimeout: Long,
        readTimeout: Long
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        interceptors?.forEach { builder.addInterceptor(it) }

        return builder
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun makeGson(baseDateTimeFormat: String): Gson {
        val gsonBuilder = GsonBuilder()
        if (baseDateTimeFormat.isEmpty()) {
            gsonBuilder.registerTypeAdapter(
                LocalDateTime::class.java,
                DateTimeSerializer(baseDateTimeFormat)
            )
        }

        return gsonBuilder.create()
    }

    fun makeLoggingInterceptor() = LoggingInterceptor.Builder()
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .tag("OkHttp")
        .build()

    fun getInterceptors(isDebug: Boolean): ArrayList<Interceptor> {
        val interceptors: ArrayList<Interceptor> = ArrayList()
        with(interceptors) {
            add(HeaderInterceptor())
            if (isDebug) add(makeLoggingInterceptor())
        }

        return interceptors
    }

    // provide ApiService
    single {
        makeAsyncApiService(
            SCHEME,
            BASE_URL,
            HTTP_PORT,
            get(),
            get(),
            ApiService::class.java
        )
    }

    // provide GsonConverter
    single {
        makeGson(BASE_DATE_TIME_FORMAT)
    }

    // provide OkHttpClient for HTTP connection
    single {
        val interceptors = getInterceptors(IS_DEBUG)
        makeHttpClient(
            interceptors,
            CONNECT_TIMEOUT,
            WRITE_TIMEOUT,
            READ_TIMEOUT
        )
    }

    // provide RemoteDataSource
    single {
        RemoteDataSource(get())
    }

    /**
     * Interceptor repository
     */
    single { InterceptorRepository(get()) }
}