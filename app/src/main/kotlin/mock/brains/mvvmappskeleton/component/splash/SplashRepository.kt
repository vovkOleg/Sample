package mock.brains.mvvmappskeleton.component.splash

import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class SplashRepository(
    private val preferences: SharedPreferencesDataSource
) {

    fun isUserLoggedIn(): Boolean =
        preferences.accessToken.isNotBlank()
}