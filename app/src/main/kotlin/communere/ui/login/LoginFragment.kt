package communere.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import communere.R
import communere.data.Authenticate
import communere.data.DataSource
import communere.databinding.FragmentLoginBinding
import communere.ui.base.BaseFragment
import meow.core.api.HttpCodes
import meow.core.api.createErrorModel
import meow.core.arch.MeowFlow
import meow.ktx.*
import org.kodein.di.erased.instance

/**
 * Login Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val dataSource: DataSource by instance<DataSource>()

    private val viewModel: LoginViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAction.setOnClickListener {
            binding.formView.validate {
                callApiAndObserve()
            }
        }
    }

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.apply {
            etUsername.apiField = "username"
            etPassword.apiField = "password"
        }
    }

    private fun callApiAndObserve() {
        MeowFlow.PutDataApi<Authenticate.Api.ResponseGetToken>(this) {
            val request = Authenticate.Api.RequestGetToken(
                username = binding.etUsername.textString,
                password = binding.etPassword.textString
            )
            viewModel.callApi(request)
        }.apply {
            containerViews = arrayOf(binding.formView)
            progressBarInterface = binding.pb
            onRequestNotValidFromResponse = {
                binding.formView.showErrorFromApi(it)
            }
            onErrorAction = {
                logD(m = "api login error ${it.data.code}")
                when (it.data.code) {
                    HttpCodes.UNAUTHORIZED.code -> toastL(R.string.warn_login_unauthorized)
                    else -> toastL(it.data.createErrorModel(resources()).titlePlusMessage)
                }
            }
            onSuccessAction = {
                logD(m = "api login success ${it.token}")
                if (it.token.isNotNullOrEmpty()) {
                    binding.formView.resetForm()
                    toastL(R.string.warn_login_success)
                    findNavController().navigate(R.id.fragmentHome)
                } else
                    toastL(R.string.warn_login_failed)
            }
        }.observeForForm(viewModel.eventLiveData)
    }

    override fun onKeyboardStateChanged(isKeyboardUp: Boolean, isFromOnCreate: Boolean) {
        binding.llLogo.visibility = if (isKeyboardUp) View.GONE else View.VISIBLE
    }
}