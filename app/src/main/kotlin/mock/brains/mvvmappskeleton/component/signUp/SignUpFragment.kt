package mock.brains.mvvmappskeleton.component.signUp

import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.fragment_sign_up.*
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.fragment.BaseFragment
import mock.brains.mvvmappskeleton.core.extension.debug
import mock.brains.mvvmappskeleton.core.extension.hideKeyboard
import mock.brains.mvvmappskeleton.core.extension.observe
import mock.brains.mvvmappskeleton.core.extension.setDebounceOnClickListener
import mock.brains.mvvmappskeleton.util.DotPasswordTransformationMethod
import mock.brains.mvvmappskeleton.util.DotSize
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {

    override val viewModel: SignUpViewModel by viewModel(parameters = { parametersOf(SignUpFragmentArgs.fromBundle(requireArguments())) })

    override fun initializeViews() {
        signUp_toolbar.setNavigationOnClickListener { viewModel.goBack() }
        signUp_emailField.doAfterTextChanged { viewModel.setEmail(it?.toString().orEmpty()) }
        with(signUp_passwordField) {
            transformationMethod = DotPasswordTransformationMethod(DotSize.BIG)
            doAfterTextChanged {
                viewModel.setPassword(it?.toString().orEmpty())
            }
        }
        with(signUp_confirmPasswordField) {
            transformationMethod = DotPasswordTransformationMethod(DotSize.BIG)
            doAfterTextChanged {
                viewModel.setConfirmPassword(it?.toString().orEmpty())
            }
        }
        signUp_signUpButton.setDebounceOnClickListener {
            hideKeyboard()
            viewModel.signUp()
        }

        debug {
            signUp_emailField.setText("michael.lawson@reqres.in")
            signUp_passwordField.setText("someSecurePassword")
            signUp_confirmPasswordField.setText("someSecurePassword")
        }
    }

    override fun onBindLiveData() {
        super.onBindLiveData()
        observe(viewModel.email) {
            if (it != signUp_emailField.text.toString()) signUp_emailField.setText(it)
        }
    }
}