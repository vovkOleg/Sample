package mock.brains.mvvmappskeleton.worker

import org.koin.dsl.module

val workerModule = module {

    single { PushUpdateWorkerRepository(get()) }
}