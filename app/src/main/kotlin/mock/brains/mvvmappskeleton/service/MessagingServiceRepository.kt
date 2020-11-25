package mock.brains.mvvmappskeleton.service

import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class MessagingServiceRepository(
    private val preferences: SharedPreferencesDataSource
) {

    fun setPushToken(token: String?) {
        preferences.pushToken = token ?: ""
    }

    fun getToken(): String =
        preferences.accessToken
}