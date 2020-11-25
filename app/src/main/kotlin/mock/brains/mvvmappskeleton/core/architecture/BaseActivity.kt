package mock.brains.mvvmappskeleton.core.architecture

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavArgs
import com.google.android.material.snackbar.Snackbar
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.dialog.AlertDialogFragment
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.extension.hideKeyboard
import mock.brains.mvvmappskeleton.core.extension.toast
import mock.brains.mvvmappskeleton.widget.LoaderOverlay
import timber.log.Timber

abstract class BaseActivity<out VM : BaseViewModel<NavArgs>>(
    @LayoutRes private val layoutResourceId: Int
) : AppCompatActivity(), BaseActivityCallback {

    protected abstract val viewModel: VM
    private var alertDialog: AlertDialogFragment? = null
    protected lateinit var rootView: ViewGroup
    private lateinit var loaderOverlay: LoaderOverlay

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(ANIM_ENTER, ANIM_EXIT)
        super.onCreate(savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.d("onCreate()")
        lifecycle.addObserver(viewModel)

        if (layoutResourceId != 0) {
            setContentView(layoutResourceId)
        }
        rootView = findViewById(android.R.id.content)
        loaderOverlay = LoaderOverlay(this)
            .apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        rootView.addView(loaderOverlay)

        initializeViews()
        onBindLiveData()
    }

    @CallSuper
    override fun finish() {
        super.finish()
        overridePendingTransition(ANIM_ENTER, ANIM_EXIT)
    }

    @CallSuper
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(ANIM_ENTER, ANIM_EXIT)
    }

    @CallSuper
    override fun onDestroy() {
        Timber.tag(this::class.java.simpleName)
        Timber.d("onDestroy()")
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    /**
     * Here we may initialize screen views
     * This method will be executed after parent [onCreate] method
     */
    protected abstract fun initializeViews()

    /**
     * Here we may bind our observers to LiveData if some.
     * This method will be executed after parent [onCreate] method
     */
    protected open fun onBindLiveData() {
        //Optional
    }

    override fun setLoading(inProgress: Boolean) {
        loaderOverlay.visible = inProgress
    }

    override fun showMessage(messageData: MessageData) {
        messageData.printLog()
        when (messageData) {
            is MessageData.ToastMessage -> {
                toast(messageData.message, messageData.length)
            }
            is MessageData.SnackBarMessage -> {
                val snackbar = Snackbar.make(
                    rootView,
                    messageData.message,
                    if (!messageData.positiveButtonText.isNullOrBlank()) Snackbar.LENGTH_INDEFINITE else messageData.length
                )
                if (!messageData.positiveButtonText.isNullOrBlank()) {
                    snackbar.setAction(messageData.positiveButtonText) {
                        messageData.positiveButtonListener?.invoke()
                    }
                }
                snackbar.show()
            }
            is MessageData.DialogMessage -> {
                hideKeyboard()
                alertDialog?.dismissAllowingStateLoss()
                alertDialog = AlertDialogFragment.newInstance(messageData)
                    .also {
                        it.show(supportFragmentManager)
                    }
            }
        }
    }

    companion object {

        private const val ANIM_EXIT = R.anim.fade_out
        private const val ANIM_ENTER = R.anim.fade_in
    }
}