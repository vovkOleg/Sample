package mock.brains.mvvmappskeleton.worker

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import mock.brains.mvvmappskeleton.core.network.CallStateEmpty
import mock.brains.mvvmappskeleton.data.remote.RemoteDataSource
import mock.brains.mvvmappskeleton.core.network.safeApiCall
import timber.log.Timber

class PushUpdateWorkerRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun updatePushToken(): Flow<CallStateEmpty> =
        safeApiCall {
            // TODO implement push token update method
            Timber.d("STUB: Push update started")
            delay(5000)
            Timber.d("STUB: Push update result")
            Any()
        }
}