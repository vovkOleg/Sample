package mock.brains.mvvmappskeleton.component.signUp

import androidx.lifecycle.SavedStateHandle
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {

    single { SignUpRepository(get(), get()) }
    viewModel { (args: SignUpFragmentArgs) -> SignUpViewModel(args, get(), androidContext()) }
    fragment { SignUpFragment() }
}