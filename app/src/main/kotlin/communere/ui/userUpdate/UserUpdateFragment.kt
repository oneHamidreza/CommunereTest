package communere.ui.userUpdate

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import communere.R
import communere.data.ApiEvent
import communere.data.DataSource
import communere.data.User
import communere.data.UserUpdate
import communere.databinding.FragmentUserUpdateBinding
import communere.ui.base.BaseFragment
import communere.utils.setEnabledWithAlpha
import meow.ktx.fromJson
import meow.ktx.instanceViewModel
import meow.ktx.safeObserve
import meow.ktx.toastL
import org.kodein.di.erased.instance

/**
 * User Update Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class UserUpdateFragment : BaseFragment<FragmentUserUpdateBinding>() {

    private val args by navArgs<UserUpdateFragmentArgs>()
    var model: User? = null

    private val viewModel: UserUpdateViewModel by instanceViewModel()
    private val dataSource by instance<DataSource>()

    override fun layoutId() = R.layout.fragment_user_update

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = args.model.fromJson()

        viewModel.eventLiveData.safeObserve(this) {
            when (it) {
                is ApiEvent.Loading -> {
                    showOrHideLoading(it.data)
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

        viewModel.deleteEventLiveData.safeObserve(this) {
            when (it) {
                is ApiEvent.Loading -> {
                    showOrHideLoading(it.data)
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

        binding.btAction.setOnClickListener {
            val request = UserUpdate.Api.RequestUpdate(
                username = binding.etUsername.textString.trim(),
                email = binding.etEmail.textString,
            )
            if (request.validate())
                callApi(request)
            else
                toastL(R.string.warn_userUpdate_invalid)
        }

        binding.btActionDelete.setOnClickListener {
            callApiDelete()
        }

        viewModel.fetchData()
        viewModel.userLiveData.safeObserve(this) {
            if (model == null) // Only users
                updateUI(it)
        }
        if (model != null)
            updateUI(model!!)
    }

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    private fun callApi(request: UserUpdate.Api.RequestUpdate) {
        viewModel.callApi(request)
    }

    private fun callApiDelete() {
        viewModel.deleteUserApi()
    }

    private fun showOrHideLoading(isShowing: Boolean) {
        if (isShowing) binding.pb.show() else binding.pb.hide()
    }

    private fun updateUI(user: User) {
        binding.apply {
            etEmail.getEditText()?.setText(user.email?.trim() ?: "")
            etUsername.getEditText()?.setText(user.username?.trim() ?: "")
            dataSource.fetchUser().isAdmin.apply {
                etEmail.setEnabledWithAlpha(!this)
                etUsername.setEnabledWithAlpha(!this)
                btAction.visibility = if (this) View.GONE else View.VISIBLE
                btActionDelete.visibility = if (this) View.GONE else View.VISIBLE
            }
        }
    }
}