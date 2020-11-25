package mock.brains.mvvmappskeleton.core.architecture.extra

import androidx.navigation.NavArgs

sealed class NothingNavArgs : NavArgs {
    object INSTANCE : NothingNavArgs()
}