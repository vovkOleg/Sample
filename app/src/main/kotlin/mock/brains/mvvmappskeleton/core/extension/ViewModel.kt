package mock.brains.mvvmappskeleton.core.extension

import androidx.lifecycle.ViewModel
import mock.brains.mvvmappskeleton.BuildConfig

inline fun ViewModel.debug(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}