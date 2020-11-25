package mock.brains.mvvmappskeleton.core.architecture.dialog

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.extension.getFragmentTag
import mock.brains.mvvmappskeleton.core.extension.inputMethodManager

abstract class BaseDialogFragment(
    @StyleRes protected val dialogAnimationStyle: Int = R.style.FadeDialogAnimation
) : DialogFragment() {

    @CallSuper
    open fun show(manager: FragmentManager) {
        val tag = getFragmentTag()
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag)
        }
    }

    protected fun hideKeyboard() {
        dialog?.currentFocus
            ?.let {
                context?.inputMethodManager
                    ?.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
    }

    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
        if (dialogAnimationStyle != 0) {
            dialog?.window
                ?.attributes
                ?.windowAnimations = dialogAnimationStyle
        }
    }
}