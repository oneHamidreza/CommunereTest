package communere.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import communere.R
import communere.data.ApiEvent
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

    private val viewModel: LoginViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAction.setOnClickListener {
            val request = Authenticate.Api.RequestLogin(
                username = binding.etUsername.textString.trim(),
                password = binding.etPassword.textString
            )
            if (request.validate())
                callApiAndObserve(request)
            else
                toastL(R.string.warn_login_unauthorized)
        }
    }

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.apply {
            etUsername.apiField = "username"
            etPassword.apiField = "password"
        }
    }

    private fun callApiAndObserve(request: Authenticate.Api.RequestLogin) {
        viewModel.eventLiveData.safeObserve(this) {
            when (it) {
                is ApiEvent.Loading -> {
                    if (it.data)
                        binding.pb.show()
                    else
                        binding.pb.hide()
                }
                is ApiEvent.Success -> {
                    toastL(R.string.warn_login_success)
                    findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentHome())
                }
                is ApiEvent.Error -> {
                    val data = it.data as? Authenticate.Api.ResponseLogin
                    toastL(data?.message ?: getString(R.string.warn_login_failed))
                }
                else -> {
                }
            }
        }
        viewModel.callApi(request)
    }

    override fun onKeyboardStateChanged(isKeyboardUp: Boolean, isFromOnCreate: Boolean) {
        binding.llLogo.visibility = if (isKeyboardUp) View.GONE else View.VISIBLE
    }
}