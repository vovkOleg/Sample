package mock.brains.mvvmappskeleton.component.welcome

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.architecture.extra.NothingNavArgs
import mock.brains.mvvmappskeleton.core.extension.isEmail

class WelcomeViewModel(
    private val repository: WelcomeRepository,
    private val context: Context
) : BaseViewModel<NothingNavArgs>(
    NothingNavArgs.INSTANCE
) {

    private var email: String = ""
    private var password: String = ""

    fun setEmail(value: String) {
        email = value
    }

    fun setPassword(value: String) {
        password = value
    }

    fun signIn() {
        if (validateFields()) {
            viewModelScope.launch {
                repository.login(email, password)
                    .collect {
                        callStateHandler(
                            context = context,
                            callState = it,
                            onSuccess = {
                                navigateTo(NavCommand.To(WelcomeFragmentDirections.actionWelcomeFragmentToDashboardFragment()))
                            })
                    }
            }
        }
    }

    private fun validateFields(): Boolean {
        val errorResId = when {
            email.isEmpty() -> R.string.error_email_cant_be_empty
            email.isEmail().not() -> R.string.error_email_wrong_format
            password.isEmpty() -> R.string.error_password_cant_be_empty
            else -> null
        }
        return if (errorResId == null) {
            true
        } else {
            showMessage(MessageData.DialogMessage(message = context.getString(errorResId)))
            false
        }
    }

    fun signUp() = navigateTo(NavCommand.To(WelcomeFragmentDirections.actionWelcomeFragmentToSighUpFragment(email)))
}