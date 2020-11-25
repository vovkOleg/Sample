package mock.brains.mvvmappskeleton.component.welcome

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val welcomeModule = module {

    single { WelcomeRepository(get(), get()) }
    viewModel { WelcomeViewModel(get(), androidContext()) }
    fragment { WelcomeFragment() }
}