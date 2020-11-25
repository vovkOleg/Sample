package mock.brains.mvvmappskeleton.core.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mock.brains.mvvmappskeleton.core.network.CallState

open class ObserveGeneral<DATA>(
    var onEmmit: (DATA.() -> Unit) = {},
    var onError: ((Throwable) -> Unit)? = null, // TODO change error type
    var liveData: LiveData<CallState<DATA>> = MutableLiveData(),
    var onProgress: (() -> Unit)? = null
)