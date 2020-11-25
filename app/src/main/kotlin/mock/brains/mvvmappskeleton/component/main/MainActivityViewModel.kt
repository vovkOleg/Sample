package mock.brains.mvvmappskeleton.component.main

import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.NothingNavArgs

class MainActivityViewModel(
) : BaseViewModel<NothingNavArgs>(
    NothingNavArgs.INSTANCE
)