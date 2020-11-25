package mock.brains.mvvmappskeleton.core.extension

import android.graphics.Rect
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.animateGone(duration: Long = 100) {
    if (visibility != View.GONE) {
        animate().setDuration(duration)
            .alpha(0f)
            .withEndAction {
                visibility = View.GONE
            }
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.animateVisible(duration: Long = 100) {
    if (visibility != View.VISIBLE) {
        alpha = 0f
        visibility = View.VISIBLE
        animate().setDuration(duration)
            .alpha(1f)
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.animateInvisible(duration: Long = 100) {
    if (visibility != View.INVISIBLE) {
        animate().setDuration(duration)
            .alpha(0f)
            .withEndAction {
                visibility = View.INVISIBLE
            }
    }
}

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.switchVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

fun View.setDebounceOnClickListener(interval: Long = 400, listener: View.() -> Unit) {
    var lastClick = 0L
    setOnClickListener { v ->
        SystemClock.uptimeMillis().apply {
            if (minus(lastClick) > interval) listener.invoke(v)
            lastClick = this
        }
    }
}

val View.isKeyboardOpen
    get() = Rect().run {
        val screenHeight = rootView.height
        getWindowVisibleDisplayFrame(this)
        screenHeight - bottom > screenHeight * 0.15
    }

fun View.hideKeyboard(needClearFocus: Boolean = true) =
    context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        .also { if (needClearFocus) clearFocus() }

fun View.imitateRealClick() {
    postDelayed({
        dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0))
        dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0))
        if (this is EditText) setSelection(text?.length ?: 0)
    }, 100L)
}

fun EditText.text(): String = text.toString()

/**
 * Validates if the [TextInputLayout.getEditText] is null or empty, and sets the error message to the
 * given message if so
 */
fun TextInputLayout.validateNotEmpty(errorMessage: String): Boolean {
    return if (editText!!.text.isNullOrEmpty()) {
        error = errorMessage
        false
    } else {
        error = null
        true
    }
}

/**
 * Retrieves the text from the TextInputLayout's EditText
 */
fun TextInputLayout.text(): String {
    return editText!!.text.toString()
}