package mock.brains.mvvmappskeleton.data.remote.interceptor

import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class InterceptorRepository(
    private val preferences: SharedPreferencesDataSource
) {

    fun getToken(): String =
        preferences.accessToken
}