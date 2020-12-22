package communere.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import communere.R
import communere.data.ApiEvent
import communere.data.Authenticate
import communere.data.User
import communere.databinding.FragmentLoginBinding
import communere.ui.base.BaseFragment
import meow.ktx.instanceViewModel
import meow.ktx.safeObserve
import meow.ktx.toastL

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

        binding.btActionRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRegister())
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
                    val user = it.data as User
                    if (user.isAdmin) {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.fragmentUserIndex)
                    } else {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.fragmentUserUpdate)
                    }
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