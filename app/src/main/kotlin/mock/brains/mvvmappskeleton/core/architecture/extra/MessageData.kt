package mock.brains.mvvmappskeleton.core.architecture.extra

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

sealed class MessageData(
    private val type: String,
    private val message: String?
) {

    data class ToastMessage(
        val message: String,
        val length: Int = Toast.LENGTH_LONG
    ) : MessageData("Toast", message)

    data class SnackBarMessage(
        val message: String,
        val length: Int = Snackbar.LENGTH_LONG,
        val positiveButtonText: String? = null,
        val positiveButtonListener: (() -> Unit)? = null
    ) : MessageData("SnackBar", message)

    data class DialogMessage(
        val title: String? = null,
        val message: String?,
        val isCancellable: Boolean? = null,
        val positiveButtonText: String? = null,
        val positiveButtonListener: (() -> Unit)? = null,
        val negativeButtonText: String? = null,
        val negativeButtonListener: (() -> Unit)? = null
    ) : MessageData("Dialog", message)

    data class FieldMessage(
        val fieldName: String?,
        val message: String?
    ) : MessageData("Field", message)

    fun printLog() {
        Timber.d("$type message:> $message")
    }
}