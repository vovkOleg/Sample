package mock.brains.mvvmappskeleton.core.architecture

import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import androidx.navigation.NavArgs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.general.SingleLiveEvent
import mock.brains.mvvmappskeleton.core.network.*
import timber.log.Timber

open class BaseViewModel<out NA : NavArgs>(
    protected val arguments: NA
) : ViewModel(), LifecycleObserver {

    private val _navigationCommand by lazy { SingleLiveEvent<NavCommand>() }
    val navigationCommand: LiveData<NavCommand> by lazy { _navigationCommand }

    private val _progressLoadingEvent by lazy { MutableLiveData<Boolean>() }
    val progressLoadingEvent: LiveData<Boolean> by lazy { _progressLoadingEvent }

    private val _messageDataEvent by lazy { SingleLiveEvent<MessageData>() }
    val messageDataEvent: LiveData<MessageData> by lazy { _messageDataEvent }

    open fun goBack() {
        navigateTo(NavCommand.Back)
    }

    protected fun setLoading(loading: Boolean) {
        _progressLoadingEvent.postValue(loading)
    }

    protected fun showMessage(messageData: MessageData) {
        _messageDataEvent.postValue(messageData)
    }

    protected fun navigateTo(command: NavCommand) {
        Timber.d(
            "Navigate:> ${
            when (command) {
                is NavCommand.To -> "ID ${command.directions.actionId}"
                is NavCommand.ToCustom -> "CUSTOM ${command.key}"
                else -> "BACK "
            }}"
        )
        _navigationCommand.value = command
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Timber.tag(this::class.java.simpleName)
        Timber.d("onCleared()")
    }

    protected fun <T> callStateHandler(
        context: Context? = null,
        callState: CallState<T?>,
        onSuccess: ((T?) -> Unit)? = null,
        onFailure: ((Throwable?) -> Unit)? = null
    ) {
        setLoading(callState.isLoading())
        when {
            callState.isSuccess() -> onSuccess?.invoke(callState.data)
            callState.isFailure() -> {
                if (onFailure != null) {
                    onFailure.invoke(callState.e)
                } else {
                    var message = callState.asFailure().e?.message ?: context?.getString(R.string.error_something_went_wrong) ?: "Error occurred"
                    if (message.startsWith("\"") && message.endsWith("\"")) { // for some cases with errors
                        message = message.drop(1).dropLast(1)
                    }
                    showMessage(MessageData.DialogMessage(message = message))
                }
            }
        }
    }

    protected fun <R> requestSingleLiveData(
        needLoading: Boolean = true,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        request: suspend () -> R
    ) = SingleLiveEvent<R>().switchMap { requestLiveData(needLoading, dispatcher, request) }

    protected fun <R> requestLiveData(
        needLoading: Boolean = true,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        request: suspend () -> R
    ) = liveData(viewModelScope.coroutineContext + dispatcher) {
        if (needLoading) {
            emit(CallState.Loading())
        }
        try {
            emit(CallState.Success(request.invoke()))
        } catch (e: Exception) {
            // TODO handle error
//            emit(CallState.Failure(errorHandler.checkThrowable(e)))
        }
    }
}