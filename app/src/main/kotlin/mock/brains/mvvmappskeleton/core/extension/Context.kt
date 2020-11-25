package mock.brains.mvvmappskeleton.core.extension

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.telecom.ConnectionService
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import mock.brains.mvvmappskeleton.BuildConfig
import timber.log.Timber

fun Context.compatColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

inline fun Context.debug(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_LONG) {
    val toast = Toast.makeText(this, resId, duration)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Context.convertDpToPixel(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.convertPixelToDp(px: Float?) =
    px?.let { px / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT) }

fun Context.hasNetworkConnection(): Boolean {
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.intentSafe(intent: Intent): Boolean {
    val activities = packageManager
        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return activities.isNotEmpty()
}

fun Context.compatDrawable(@DrawableRes drawableRes: Int): Drawable? =
    try {
        ContextCompat.getDrawable(this, drawableRes)
    } catch (e: Resources.NotFoundException) {
        null
    }

@DrawableRes
fun Context.drawableByName(drawableResName: String) =
    try {
        resources.getIdentifier(drawableResName, "drawable", packageName)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.inflate(
    @LayoutRes resId: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View =
    inflater.inflate(resId, root, attachToRoot)

val Context.connectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.windowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

val Context.notificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.inputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

val Context.screenHeight
    get() = Point().run {
        windowManager.defaultDisplay.getSize(this)
        y
    }

val Context.screenWidth
    get() = Point().run {
        windowManager.defaultDisplay.getSize(this)
        x
    }