package mock.brains.mvvmappskeleton.core.extension

import android.content.Context
import android.util.Base64
import android.util.Patterns
import android.util.TypedValue
import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val CHARSET_UTF_8 = "UTF-8"

fun Int.toDp(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)

fun Boolean?.falseIfNull() = this ?: false

fun Boolean?.trueIfNull() = this ?: true

fun Boolean?.toInt() = if (this == true) 1 else 0

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.decode64(): String =
    Base64.decode(this, Base64.DEFAULT)
        .toString(charset(CHARSET_UTF_8))

fun String.encode64(): String =
    Base64.encodeToString(this.toByteArray(charset(CHARSET_UTF_8)), Base64.DEFAULT)
        .replace("\n", "")

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Long.secondsToTimeString(): String {
    val duration = LocalTime.ofSecondOfDay(this)
    val pattern = if (duration.hour == 0) "mm:ss" else "HH:mm:ss"
    return DateTimeFormatter.ofPattern(pattern).format(duration)
}