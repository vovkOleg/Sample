package mock.brains.mvvmappskeleton.component.welcome

import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.fragment_welcome.*
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.core.architecture.fragment.BaseFragment
import mock.brains.mvvmappskeleton.core.extension.debug
import mock.brains.mvvmappskeleton.core.extension.hideKeyboard
import mock.brains.mvvmappskeleton.core.extension.setDebounceOnClickListener
import mock.brains.mvvmappskeleton.util.DotPasswordTransformationMethod
import mock.brains.mvvmappskeleton.util.DotSize
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeViewModel>(R.layout.fragment_welcome) {

    override val viewModel: WelcomeViewModel by viewModel()

    override fun initializeViews() {
        welcome_emailField.doAfterTextChanged { viewModel.setEmail(it?.toString()?.trim().orEmpty()) }
        with(welcome_passwordField) {
            transformationMethod = DotPasswordTransformationMethod(DotSize.BIG)
            doAfterTextChanged {
                viewModel.setPassword(it?.toString()?.trim().orEmpty())
            }
        }
        welcome_signInButton.setDebounceOnClickListener {
            hideKeyboard()
            viewModel.signIn()
        }
        welcome_signUpButton.setDebounceOnClickListener {
            hideKeyboard()
            viewModel.signUp()
        }
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        debug {
            welcome_emailField.setText("michael.lawson@reqres.in")
            welcome_passwordField.setText("someSecurePassword")
        }
    }
}