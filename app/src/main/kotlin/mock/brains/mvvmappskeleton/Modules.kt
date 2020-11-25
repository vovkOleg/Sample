package mock.brains.mvvmappskeleton

import mock.brains.mvvmappskeleton.component.dashboard.dashboardModule
import mock.brains.mvvmappskeleton.component.main.mainModule
import mock.brains.mvvmappskeleton.component.signUp.signUpModule
import mock.brains.mvvmappskeleton.component.splash.splashModule
import mock.brains.mvvmappskeleton.component.welcome.welcomeModule
import mock.brains.mvvmappskeleton.data.database.databaseModule
import mock.brains.mvvmappskeleton.data.remote.remoteModule
import mock.brains.mvvmappskeleton.data.shared.sharedPreferencesModule
import mock.brains.mvvmappskeleton.service.messagingServiceModule
import mock.brains.mvvmappskeleton.worker.workerModule
import org.koin.core.module.Module

fun getAppModules(): List<Module> {

    val repositoryModules = listOf(
        remoteModule,
        databaseModule,
        sharedPreferencesModule
    )

    val componentModules = listOf(
        mainModule,
        splashModule,
        welcomeModule,
        signUpModule,
        dashboardModule
    )

    val serviceModules = listOf(
        messagingServiceModule,
        workerModule
    )

    return repositoryModules + componentModules + serviceModules
}