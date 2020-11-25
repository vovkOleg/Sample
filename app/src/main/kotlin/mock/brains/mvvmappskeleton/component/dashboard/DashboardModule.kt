package mock.brains.mvvmappskeleton.component.dashboard

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {

    single { DashboardRepository(get()) }
    viewModel { DashboardViewModel(get(), androidContext()) }
    fragment { DashboardFragment() }
}