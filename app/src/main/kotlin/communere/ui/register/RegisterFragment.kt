package communere.ui.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import communere.R
import communere.data.ApiEvent
import communere.data.Authenticate
import communere.databinding.FragmentRegisterBinding
import communere.ui.base.BaseFragment
import communere.utils.hideKeyboard
import meow.ktx.instanceViewModel
import meow.ktx.safeObserve
import meow.ktx.toastL

/**
 * Register Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_register

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAction.setOnClickListener {
            val request = Authenticate.Api.RequestRegister(
                fullname = binding.etFullname.textString.trim(),
                username = binding.etUsername.textString.trim(),
                password = binding.etPassword.textString,
                passwordConfirm = binding.etPassword.textString
            )
            if (request.validate())
                callApi(request)
            else
                toastL(R.string.warn_register_invalid)
        }

        viewModel.eventLiveData.safeObserve(viewLifecycleOwner) {
            when (it) {
                is ApiEvent.Loading -> {
                    if (it.data)
                        binding.pb.show()
                    else
                        binding.pb.hide()
                }
                is ApiEvent.Success -> {
                    hideKeyboard()
                    toastL(R.string.warn_register_success)
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.fragmentUserUpdate)
                }
                is ApiEvent.Error -> {
                    val data = it.data as? Authenticate.Api.ResponseLogin
                    toastL(data?.message ?: getString(R.string.warn_register_failed))
                }
                else -> {
                }
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

    private fun callApi(request: Authenticate.Api.RequestRegister) {
        viewModel.callApi(request)
    }
}