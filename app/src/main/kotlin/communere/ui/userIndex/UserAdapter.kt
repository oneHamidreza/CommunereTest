/*
 * Copyright (C) 2020 Hamidreza Etebarian.
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

import android.view.LayoutInflater
import android.view.ViewGroup
import communere.BR
import communere.data.User
import communere.databinding.ItemUserBinding
import meow.core.ui.MeowAdapter
import meow.core.ui.MeowViewHolder

/**
 * User Adapter.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

typealias Model = User

typealias ViewHolder = MeowViewHolder<Model>
typealias DiffCallback = User.DiffCallback

class UserAdapter(var viewModel: UserIndexViewModel, var onClickedItem: (model: Model) -> Unit) :
    MeowAdapter<Model, ViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeowViewHolder(binding.root) { _, model ->
            binding.let {
                it.ivRemove.setOnClickListener {
                    viewModel.removeItem(model)
                }
                binding.root.setOnClickListener {
                    onClickedItem(model)
                }

                it.setVariable(BR.model, model)
                it.executePendingBindings()
            }
        }
    }
}
