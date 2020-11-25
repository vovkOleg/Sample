package mock.brains.mvvmappskeleton.core.general

import android.view.ViewTreeObserver
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import mock.brains.mvvmappskeleton.core.extension.getRootView
import mock.brains.mvvmappskeleton.core.extension.isKeyboardOpen

class KeyboardEventListener(
    private val activity: FragmentActivity,
    private val lifecycle: Lifecycle,
    private val callback: (isOpen: Boolean) -> Unit
) : LifecycleObserver {

    private val listener = object : ViewTreeObserver.OnGlobalLayoutListener {

        private var lastState: Boolean = activity.isKeyboardOpen()

        override fun onGlobalLayout() {
            val isOpen = activity.isKeyboardOpen()
            if (isOpen == lastState) {
                return
            } else {
                dispatchKeyboardEvent(isOpen)
                lastState = isOpen
            }
        }
    }

    init {
        dispatchKeyboardEvent(activity.isKeyboardOpen())
        lifecycle.addObserver(this)
        registerKeyboardListener()
    }

    private fun registerKeyboardListener() {
        activity.getRootView()
            .viewTreeObserver
            .addOnGlobalLayoutListener(listener)
    }

    private fun dispatchKeyboardEvent(isOpen: Boolean) {
        when {
            isOpen -> callback(true)
            !isOpen -> callback(false)
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    @CallSuper
    fun onLifecyclePause() {
        activity.getRootView()
            .viewTreeObserver
            .removeOnGlobalLayoutListener(listener)
        lifecycle.removeObserver(this)
    }
}