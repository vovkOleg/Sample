package mock.brains.mvvmappskeleton.core.architecture.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import mock.brains.mvvmappskeleton.core.architecture.BaseActivityCallback
import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.extension.hideKeyboard
import mock.brains.mvvmappskeleton.core.extension.observe
import timber.log.Timber

abstract class BaseFragment<out VM : BaseViewModel<NavArgs>>(@LayoutRes layoutResourceId: Int) : Fragment(layoutResourceId) {

    protected abstract val viewModel: VM
    protected open var customNavigationHandler: ((NavCommand.ToCustom) -> Unit)? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.d("onViewCreated()")
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        initializeViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onBindLiveData()
    }

    @CallSuper
    override fun onDestroyView() {
        Timber.tag(this::class.java.simpleName)
        Timber.d("onDestroyView()")
        viewLifecycleOwner.lifecycle.removeObserver(viewModel)
        super.onDestroyView()
    }

    /**
     * Here we may initialize screen views
     * This method will be executed after parent [onCreateView] method
     */
    protected abstract fun initializeViews()

    /**
     * Here we may bind our observers to LiveData if some.
     * This method will be executed after parent [onCreateView] method
     */
    @CallSuper
    protected open fun onBindLiveData() {
        observe(viewModel.progressLoadingEvent, ::setLoading)
        observe(viewModel.messageDataEvent, ::showMessage)
        observe(viewModel.navigationCommand, ::navigateTo)
    }

    protected open fun setLoading(loading: Boolean) {
        Timber.tag(this::class.java.simpleName)
        Timber.d("setLoading> $loading")
        (requireActivity() as BaseActivityCallback).setLoading(loading)
    }

    protected fun showMessage(messageData: MessageData) {
        (requireActivity() as BaseActivityCallback).showMessage(messageData)
    }

    private fun navigateTo(command: NavCommand) {
        when (command) {
            is NavCommand.To -> findNavController().navigate(command.directions)
            is NavCommand.Back -> {
                hideKeyboard()
                findNavController().popBackStack()
            }
            is NavCommand.ToCustom -> customNavigationHandler?.invoke(command)
                ?: throw NullPointerException("Custom navigation command with key \"${command.key}\" not handled because of not specified \"onCustomCommand\" callback")
        }
    }

    protected fun attachBackPressCallback(enabled: Boolean = true, action: OnBackPressedCallback.() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                action()
            }
        })
    }
}