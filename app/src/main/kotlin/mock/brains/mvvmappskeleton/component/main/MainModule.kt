package mock.brains.mvvmappskeleton.component.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {  MainActivityViewModel() }
}