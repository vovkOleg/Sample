package mock.brains.mvvmappskeleton.core.architecture

import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData

interface BaseActivityCallback {

    fun setLoading(inProgress: Boolean)

    fun showMessage(messageData: MessageData)
}