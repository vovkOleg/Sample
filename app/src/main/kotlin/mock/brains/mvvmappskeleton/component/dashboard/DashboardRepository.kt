package mock.brains.mvvmappskeleton.component.dashboard

import mock.brains.mvvmappskeleton.data.shared.SharedPreferencesDataSource

class DashboardRepository(
    private val preferences: SharedPreferencesDataSource
) {

    fun logout() =
        preferences.clearUserData()

    fun getEmailAddress(): String =
        preferences.email
}