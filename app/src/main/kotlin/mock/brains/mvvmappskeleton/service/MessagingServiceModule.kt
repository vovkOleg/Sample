package mock.brains.mvvmappskeleton.service

import org.koin.dsl.module

val messagingServiceModule = module {

    single { MessagingServiceRepository(get()) }
}