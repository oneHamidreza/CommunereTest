package communere.ui.userUpdate

import android.os.Bundle
import android.view.View
import communere.R
import communere.data.ApiEvent
import communere.data.User
import communere.data.UserUpdate
import communere.databinding.FragmentUserUpdateBinding
import communere.ui.base.BaseFragment
import meow.ktx.instanceViewModel
import meow.ktx.safeObserve
import meow.ktx.toastL

/**
 * User Update Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class UserUpdateFragment : BaseFragment<FragmentUserUpdateBinding>() {

    private val viewModel: UserUpdateViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_user_update

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAction.setOnClickListener {
            val request = UserUpdate.Api.RequestUpdate(
                username = binding.etUsername.textString.trim(),
                email = binding.etEmail.textString,
            )
            if (request.validate())
                callApiAndObserve(request)
            else
                toastL(R.string.warn_userUpdate_invalid)
        }

        binding.btActionDelete.setOnClickListener {
            callApiDeleteAndObserve()
        }

        viewModel.fetchData()
        viewModel.userLiveData.safeObserve(this) {
            updateUI(it)
        }
    }

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun callApiAndObserve(request: UserUpdate.Api.RequestUpdate) {
        viewModel.eventLiveData.safeObserve(this) {
            when (it) {
                is ApiEvent.Loading -> {
                    if (it.data)
                        binding.pb.show()
                    else
                        binding.pb.hide()
                }
                is ApiEvent.Success -> {
                    toastL(R.string.warn_userUpdate_success)
                }
                is ApiEvent.Error -> {
                    val data = it.data as? UserUpdate.Api.ResponseUpdate
                    toastL(data?.message ?: getString(R.string.warn_userUpdate_failed))
                }
                else -> {
                }
            }
        }
        viewModel.callApi(request)
    }

    private fun callApiDeleteAndObserve() {
        viewModel.deleteEventLiveData.safeObserve(this) {
            when (it) {
                is ApiEvent.Loading -> {
                    if (it.data)
                        binding.pb.show()
                    else
                        binding.pb.hide()
                }
                is ApiEvent.Success -> {
                    toastL(R.string.warn_userUpdate_delete_success)
                    activity().recreate()
                }
                is ApiEvent.Error -> {
                    val data = it.data as? UserUpdate.Api.ResponseUpdate
                    toastL(data?.message ?: getString(R.string.warn_userUpdate_delete_failed))
                }
                else -> {
                }
            }
        }
        viewModel.deleteUserApi()
    }

    private fun updateUI(user: User) {
        binding.apply {
            etEmail.getEditText()?.setText(user.email?.trim() ?: "")
            etUsername.getEditText()?.setText(user.username?.trim() ?: "")
            user.isAdmin.apply {
                etEmail.isEnabled = !this
                etUsername.isEnabled = !this
            }
        }
    }
}