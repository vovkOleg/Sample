package mock.brains.mvvmappskeleton.core.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mock.brains.mvvmappskeleton.core.extension.compatColor
import mock.brains.mvvmappskeleton.core.extension.dimensionPixelSize
import mock.brains.mvvmappskeleton.core.extension.drawable
import mock.brains.mvvmappskeleton.core.extension.stringRes
import timber.log.Timber

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected val context: Context
        get() = itemView.context

    interface Factory<T> {
        fun create(itemView: View): BaseViewHolder<T>
    }

    abstract fun bind(item: T, position: Int)

    protected val Int.toText get() = stringRes(this)

    protected val Int.toDrawable
        get() = try {
            drawable(this)
        } catch (e: Resources.NotFoundException) {
            Timber.e(e)
            null
        }

    protected val Int.toColor get() = compatColor(this)

    protected val Int.toPixelSize get() = dimensionPixelSize(this)
}