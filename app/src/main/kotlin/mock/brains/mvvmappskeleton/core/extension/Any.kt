package mock.brains.mvvmappskeleton.core.extension

import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun Any?.parseBoolean(defValue: Boolean = false): Boolean {
    return when (this) {
        is Boolean? -> this.falseIfNull()
        is Int -> this > 0
        else -> defValue
    }
}

fun Any?.parseInt(defValue: Int = 0): Int {
    return when (this) {
        is Int -> this
        else -> defValue
    }
}

fun Any?.parseString(defValue: String? = null): String? {
    return when (this) {
        is String -> this
        else -> defValue
    }
}

fun Any?.parseDouble(defValue: Double = 0.0): Double {
    return when (this) {
        is Double -> this
        else -> defValue
    }
}

val KProperty0<*>.isLazyInitialized: Boolean
    get() {
        // Prevent IllegalAccessException from JVM access check on private properties.
        val originalAccessLevel = isAccessible
        isAccessible = true
        val isLazyInitialized = (getDelegate() as Lazy<*>).isInitialized()
        // Reset access level.
        isAccessible = originalAccessLevel
        return isLazyInitialized
    }