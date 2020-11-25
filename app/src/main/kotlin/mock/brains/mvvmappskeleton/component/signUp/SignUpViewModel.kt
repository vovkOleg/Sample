package mock.brains.mvvmappskeleton.component.signUp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.BaseViewModel
import mock.brains.mvvmappskeleton.core.architecture.extra.MessageData
import mock.brains.mvvmappskeleton.core.architecture.extra.NavCommand
import mock.brains.mvvmappskeleton.core.extension.isEmail

class SignUpViewModel(
    arguments: SignUpFragmentArgs,
    private val repository: SignUpRepository,
    private val context: Context
) : BaseViewModel<SignUpFragmentArgs>(
    arguments
) {

    private val _email by lazy { MutableLiveData(arguments.email) }
    val email: LiveData<String> by lazy { _email }

    private var password: String = ""
    private var confirmPassword: String = ""

    fun setEmail(value: String) {
        if (value != email.value) _email.value = value
    }

    fun setPassword(value: String) {
        password = value
    }

    fun setConfirmPassword(value: String) {
        confirmPassword = value
    }

    fun signUp() {
        if (validateFields()) {
            viewModelScope.launch {
                repository.registry(email.value!!, password)
                    .collect {
                        callStateHandler(
                            context = context,
                            callState = it,
                            onSuccess = {
                                navigateTo(NavCommand.To(SignUpFragmentDirections.actionSighUpFragmentToDashboardFragment()))
                            })
                    }
            }
        }
    }

    private fun validateFields(): Boolean {
        val errorResId = when {
            email.value.isNullOrBlank() -> R.string.error_email_cant_be_empty
            email.value!!.isEmail().not() -> R.string.error_email_wrong_format
            password.isEmpty() -> R.string.error_password_cant_be_empty
            confirmPassword.isEmpty() -> R.string.error_confirm_password_cant_be_empty
            password != confirmPassword -> R.string.error_passwords_does_not_match
            else -> null
        }
        return if (errorResId == null) {
            true
        } else {
            showMessage(MessageData.DialogMessage(message = context.getString(errorResId)))
            false
        }
    }
}