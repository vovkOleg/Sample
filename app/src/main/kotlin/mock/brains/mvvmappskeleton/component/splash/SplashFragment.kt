package mock.brains.mvvmappskeleton.component.splash

import kotlinx.android.synthetic.main.fragment_splash.*
import mock.brains.mvvmappskeleton.BuildConfig
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModel()

    override fun initializeViews() {
        val version = "v. ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        splash_appVersion.text = version
    }
}