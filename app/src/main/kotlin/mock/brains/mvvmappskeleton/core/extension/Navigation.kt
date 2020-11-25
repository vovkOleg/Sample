package mock.brains.mvvmappskeleton.core.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import timber.log.Timber

fun NavController.onNavDestinationSelected(@IdRes destId: Int, args: Bundle? = null, startDestination: Int = graph.startDestination, inclusive: Boolean = false) = try {
    navigate(destId, args, getNavOptions().setPopUpTo(startDestination, inclusive).build())
    true
} catch (e: IllegalArgumentException) {
    Timber.e("Destination problem: $e")
    false
}

fun NavController.onNavDestinationSelected(@IdRes destId: Int, args: Bundle? = null) = try {
    navigate(destId, args, getNavOptions().build())
    true
} catch (e: IllegalArgumentException) {
    Timber.e("Destination problem: $e")
    false
}

fun NavController.onNavDestinationSelected(directions: NavDirections) = onNavDestinationSelected(directions.actionId, directions.arguments)

private fun getNavOptions() = NavOptions.Builder()
    .setLaunchSingleTop(true)
    .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
    .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
    .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
    .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)