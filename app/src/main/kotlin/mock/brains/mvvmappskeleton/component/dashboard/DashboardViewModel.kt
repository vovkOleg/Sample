package mock.brains.mvvmappskeleton.component.dashboard

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.architecture.extra.NothingNavArgs
import mock.brains.mvvmappskeleton.component.dashboard.DashboardFragment.Companion.NAV_CONFIRM_DIALOG

class DashboardViewModel(
    private val repository: DashboardRepository,
    private val context: Context
) : BaseViewModel<NothingNavArgs>(
    NothingNavArgs.INSTANCE
) {

    fun performLogout() {
        navigateTo(NavCommand.ToCustom(NAV_CONFIRM_DIALOG, repository.getEmailAddress()))
    }

    fun logout() {
        repository.logout()
        navigateTo(NavCommand.To(DashboardFragmentDirections.actionDashboardFragmentToWelcomeFragment()))
    }
}