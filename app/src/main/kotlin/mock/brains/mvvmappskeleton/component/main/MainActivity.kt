package mock.brains.mvvmappskeleton.component.main

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity<MainActivityViewModel>(R.layout.activity_main) {

    override val viewModel: MainActivityViewModel by viewModel()

    override fun initializeViews() {
        changeDrawerLock(true) // TODO use drawer in future
        setupNavigation()
        setupDrawer()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            setLoading(false)
            Timber.d("destination> ${destination.label}")

            // TODO lock drawer if needed
        }
    }

    private fun changeDrawerLock(lock: Boolean) {
        main_drawer_layout.setDrawerLockMode(if (lock) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun setupDrawer() {
        // TODO setup drawer
    }

    private fun closeDrawer() {
        main_drawer_layout.closeDrawer(GravityCompat.START)
    }
}