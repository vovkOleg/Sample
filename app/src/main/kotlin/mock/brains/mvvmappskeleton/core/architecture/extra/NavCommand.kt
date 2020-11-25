package mock.brains.mvvmappskeleton.core.architecture.extra

import androidx.navigation.NavDirections

sealed class NavCommand {

    data class To(val directions: NavDirections) : NavCommand()

    object Back : NavCommand()

    data class ToCustom(val key: String, val data: Any? = null) : NavCommand()
}