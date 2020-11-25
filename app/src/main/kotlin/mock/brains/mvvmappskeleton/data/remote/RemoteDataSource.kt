package mock.brains.mvvmappskeleton.data.remote

import mock.brains.mvvmappskeleton.data.remote.request.AuthRequest

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun login(email: String, password: String) =
        apiService.login(AuthRequest(email, password))

    suspend fun registry(email: String, password: String) =
        apiService.registry(AuthRequest(email, password))
}