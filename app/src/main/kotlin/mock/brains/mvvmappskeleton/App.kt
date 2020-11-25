package mock.brains.mvvmappskeleton

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import mock.brains.mvvmappskeleton.core.general.CrashlyticsCrashReportingTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initCrashlytics()
        initKoin()
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun initLogger() {
        Timber.plant(
            if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsCrashReportingTree()
        )
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            fragmentFactory()
            modules(getAppModules())
        }
    }
}