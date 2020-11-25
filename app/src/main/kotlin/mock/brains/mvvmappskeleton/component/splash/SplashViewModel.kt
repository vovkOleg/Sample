package mock.brains.mvvmappskeleton.component.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.architecture.extra.NothingNavArgs
import timber.log.Timber

class SplashViewModel(
    private val repository: SplashRepository
) : BaseViewModel<NothingNavArgs>(
    NothingNavArgs.INSTANCE
) {

    init {
        performStartupChecks()
    }

    private fun performStartupChecks() {
        viewModelScope.launch {
            delay(STARTUP_DELAY)

            val direction = if (repository.isUserLoggedIn()) {
                Timber.d("Access token exist")
                SplashFragmentDirections.actionSplashFragmentToDashboardFragment()
            } else {
                Timber.d("Access token empty")
                SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
            }
            navigateTo(NavCommand.To(direction))
        }
    }

    companion object {

        private const val STARTUP_DELAY = 3000L
    }
}