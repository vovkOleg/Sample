package mock.brains.mvvmappskeleton.component.signUp

import kotlinx.coroutines.flow.Flow
import mock.brains.mvvmappskeleton.core.network.CallStateAuthorized
import mock.brains.mvvmappskeleton.data.remote.RemoteDataSource
import mock.brains.mvvmappskeleton.core.network.safeApiCall
import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class SignUpRepository(
    private val remoteDataSource: RemoteDataSource,
    private val preferences: SharedPreferencesDataSource
) {

    suspend fun registry(email: String, password: String): Flow<CallStateAuthorized> =
        safeApiCall {
            val response = remoteDataSource.registry(email, password)
            preferences.email = email
            preferences.accessToken = response.token ?: ""
            true
        }
}