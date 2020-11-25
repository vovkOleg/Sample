package mock.brains.mvvmappskeleton.data.shared

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import mock.brains.mvvmappskeleton.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val SHARED_FILE_NAME = "${BuildConfig.APPLICATION_ID}.sp"

val sharedPreferencesModule = module {

    fun makeCryptoSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context, AES256_GCM_SPEC.keystoreAlias)
            .setKeyGenParameterSpec(AES256_GCM_SPEC)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            SHARED_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // provide SharedPreferences
    single {
        makeCryptoSharedPreferences(androidApplication())
    }

    // provide SharedPreferencesDataSource
    single {
        SharedPreferencesDataSource(get())
    }
}