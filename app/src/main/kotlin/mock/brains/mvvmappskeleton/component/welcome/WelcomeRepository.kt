package mock.brains.mvvmappskeleton.component.welcome

import kotlinx.coroutines.flow.Flow
import mock.brains.mvvmappskeleton.core.network.CallStateAuthorized
import mock.brains.mvvmappskeleton.data.remote.RemoteDataSource
import mock.brains.mvvmappskeleton.core.network.safeApiCall
import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class WelcomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val preferences: SharedPreferencesDataSource
) {

    suspend fun login(email: String, password: String): Flow<CallStateAuthorized> =
        safeApiCall {
            val response = remoteDataSource.login(email, password)
            preferences.email = email
            preferences.accessToken = response.token ?: ""
            true
        }
}