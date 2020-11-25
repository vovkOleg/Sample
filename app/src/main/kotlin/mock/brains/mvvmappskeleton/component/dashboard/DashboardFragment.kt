package mock.brains.mvvmappskeleton.component.dashboard

import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_dashboard.*
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.fragment.BaseFragment
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<DashboardViewModel>(R.layout.fragment_dashboard) {

    override val viewModel: DashboardViewModel by viewModel()
    override var customNavigationHandler: ((NavCommand.ToCustom) -> Unit)? = {
        if (it.key == NAV_CONFIRM_DIALOG && it.data != null && it.data is String) showLogoutDialog(it.data)
    }

    override fun initializeViews() {
        dashboard_toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_logout) {
                viewModel.performLogout()
                true
            } else false
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun showLogoutDialog(email: String) {
        showMessage(
            MessageData.DialogMessage(
                title = getString(R.string.logoutDialog_logOut),
                message = getString(R.string.logoutDialog_description, email),
                isCancellable = true,
                positiveButtonText = getString(R.string.common_yes),
                positiveButtonListener = { viewModel.logout() },
                negativeButtonText = getString(R.string.common_no)
            )
        )
    }

    companion object {

        const val NAV_CONFIRM_DIALOG = "nav_confirm_dialog"
    }
}