package mock.brains.mvvmappskeleton.core.extension

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(layoutRes: Int, attach: Boolean = false): View = getInflater().inflate(layoutRes, this, attach)

fun ViewGroup.getInflater(): LayoutInflater = LayoutInflater.from(context)

fun ViewGroup.getViewByCoordinates(x: Float, y: Float): View? {
    (0 until this.childCount)
        .map { this.getChildAt(it) }
        .forEach {
            val bounds = Rect()
            it.getHitRect(bounds)
            if (bounds.contains(x.toInt(), y.toInt())) {
                return it
            }
        }
    return null
}

fun ViewGroup.dimensionPixelSize(@DimenRes dimenResId: Int) = resources.getDimensionPixelSize(dimenResId)

fun ViewGroup.dimension(@DimenRes dimenResId: Int) = resources.getDimension(dimenResId)

val ViewGroup.lastChild: View
    get() = getChildAt(childCount - 1)