/*
 * Copyright (C) 2020 Hamidreza Etebarian & Ali Modares.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package communere.ui.userIndex

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import communere.R
import communere.data.User
import communere.databinding.FragmentUserIndexBinding
import communere.ui.base.BaseFragment
import meow.core.arch.MeowFlow
import meow.ktx.instanceViewModel
import meow.ktx.safeObserve
import meow.ktx.toJson
import meow.ktx.toastL
import meow.widget.decoration.MeowDividerDecoration

/**
 * User Index Fragment.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-04-11
 */

class UserIndexFragment : BaseFragment<FragmentUserIndexBinding>() {

    private val viewModel: UserIndexViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_user_index

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            addItemDecoration(MeowDividerDecoration(context))
            adapter = UserAdapter(viewModel) {
                findNavController().navigate(
                    UserIndexFragmentDirections.actionFragmentUserIndexToFragmentUserUpdate(
                        it.toJson()
                    )
                )
            }
        }

        viewModel.removeItemLiveData.safeObserve(viewLifecycleOwner) {
            if (it)
                toastL(R.string.warn_userIndex_remove)
        }
    }

    override fun initViewModel() {
        binding.viewModel = viewModel

        callApiAndObserve()
    }

    private fun callApiAndObserve() {
        MeowFlow.GetDataApi<User>(this) {
            viewModel.callApi()
        }.apply {
            errorHandlerType = MeowFlow.ErrorHandlerType.EMPTY_STATE
            swipeRefreshLayout = binding.srl
            emptyStateInterface = binding.emptyState
        }.observeForIndex(viewModel.eventLiveData, viewModel.listLiveData)
    }

}