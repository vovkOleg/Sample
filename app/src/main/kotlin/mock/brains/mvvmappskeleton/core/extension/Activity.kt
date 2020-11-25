@file:Suppress("unchecked_cast")

package mock.brains.mvvmappskeleton.core.extension

import android.app.Activity
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.math.roundToInt

fun <T, LD : LiveData<T>> AppCompatActivity.observeNullable(liveData: LD, onChanged: (T?) -> Unit) {
    liveData.observe(this, Observer {
        onChanged(it)
    })
}

fun <T, LD : LiveData<T>> AppCompatActivity.observe(liveData: LD, onChanged: (T) -> Unit) {
    liveData.observe(this, {
        it?.let(onChanged)
    })
}

fun <T, LD : LiveData<T>> AppCompatActivity.observeSingle(liveData: LD, onChanged: (T?) -> Unit) {
    liveData.observe(this, object : Observer<T> {
        override fun onChanged(t: T?) {
            onChanged.invoke(t)
            liveData.removeObserver(this)
        }
    })
}

fun Activity.getScreenParams(): DisplayMetrics {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics
}

fun Activity.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Activity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) result = resources.getDimensionPixelSize(resourceId)
    return result
}

fun Activity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.hideSystemNavigation() {
    val decorView = window?.decorView
    val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    decorView?.systemUiVisibility = uiOptions
}

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = convertDpToPixel(50f).toDouble().roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}