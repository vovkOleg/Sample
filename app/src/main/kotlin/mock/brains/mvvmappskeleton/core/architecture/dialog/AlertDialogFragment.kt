package mock.brains.mvvmappskeleton.core.architecture.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData

class AlertDialogFragment : BaseDialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
    private var isCancellable: Boolean? = null
    private var positiveButtonListener: (() -> Unit)? = null
    private var negativeButtonListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), dialogAnimationStyle)
        alertDialogBuilder.setCancelable(isCancellable != null ?: false)
        if (!TextUtils.isEmpty(title)) {
            alertDialogBuilder.setTitle(title)
        }
        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(
            if (TextUtils.isEmpty(positiveButtonText)) getString(R.string.common_close) else positiveButtonText
        ) { _, _ -> positiveButtonListener?.invoke() }
        if (!TextUtils.isEmpty(negativeButtonText)) {
            alertDialogBuilder.setNegativeButton(negativeButtonText) { _, _ -> negativeButtonListener?.invoke() }
        }
        val dialog = alertDialogBuilder.create()
        dialog.setCanceledOnTouchOutside(isCancellable != null ?: false)
        dialog.setCancelable(isCancellable != null ?: false)
        isCancelable = isCancellable != null ?: false

        return dialog
    }

    companion object {

        fun newInstance(
            title: String? = null,
            message: String? = null,
            positiveButtonText: String? = null,
            negativeButtonText: String? = null,
            isCancellable: Boolean? = null,
            positiveButtonListener: (() -> Unit)? = null,
            negativeButtonListener: (() -> Unit)? = null
        ): AlertDialogFragment {
            return AlertDialogFragment().apply {
                this.title = title
                this.message = message
                this.positiveButtonText = positiveButtonText
                this.negativeButtonText = negativeButtonText
                this.isCancellable = isCancellable
                this.positiveButtonListener = positiveButtonListener
                this.negativeButtonListener = negativeButtonListener
            }
        }

        fun newInstance(
            messageData: MessageData.DialogMessage
        ): AlertDialogFragment {
            return newInstance(
                messageData.title,
                messageData.message,
                messageData.positiveButtonText,
                messageData.negativeButtonText,
                messageData.isCancellable,
                messageData.positiveButtonListener,
                messageData.negativeButtonListener
            )
        }
    }
}