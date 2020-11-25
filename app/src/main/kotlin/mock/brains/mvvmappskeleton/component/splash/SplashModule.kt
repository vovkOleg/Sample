package mock.brains.mvvmappskeleton.component.splash

import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    single { SplashRepository(get()) }
    viewModel { SplashViewModel(get()) }
    fragment { SplashFragment() }
}