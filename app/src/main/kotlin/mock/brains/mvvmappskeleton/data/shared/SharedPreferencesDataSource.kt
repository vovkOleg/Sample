package mock.brains.mvvmappskeleton.data.shared

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesDataSource(private val sharedPreferences: SharedPreferences) {

    var pushToken: String
        get() = sharedPreferences.getString(KEY_PUSH_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit { putString(KEY_PUSH_TOKEN, value) }

    var accessToken: String
        get() = sharedPreferences.getString(KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit { putString(KEY_ACCESS_TOKEN, value) }

    var email: String
        get() = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        set(value) = sharedPreferences.edit { putString(KEY_EMAIL, value) }

    fun clearUserData() {
        sharedPreferences.edit {
            putString(KEY_EMAIL, null)
            putString(KEY_ACCESS_TOKEN, null)
        }
    }

    companion object {

        private const val KEY_PUSH_TOKEN = "push_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}