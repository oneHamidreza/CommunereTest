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

import communere.App
import communere.data.User
import meow.core.api.MeowEvent
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * [User]/Index View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class UserIndexViewModel(
    override val app: App,
    private val repository: User.Repository
) : MeowViewModel(app) {

    var eventLiveData = SingleLiveData<MeowEvent<*>>()
    var listLiveData = SingleLiveData<List<User>>()
    var removeItemLiveData = SingleLiveData<Boolean>()

    fun callApi() {
        safeCallApi(
            liveData = eventLiveData,
            apiAction = { repository.getAllUsers() }
        ) { _, it ->
            listLiveData.postValue(it)
        }
    }

    fun removeItem(item: User) {
        val list = ArrayList(listLiveData.value!!)
        list.remove(item)
        listLiveData.postValue(list)
        removeItemLiveData.postValue(true)
    }
}