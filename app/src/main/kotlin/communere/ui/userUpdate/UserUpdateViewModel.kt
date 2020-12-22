package communere.ui.userUpdate

import communere.App
import communere.data.ApiEvent
import communere.data.User
import communere.data.UserUpdate
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * User Update View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-22
 */

class UserUpdateViewModel(override var app: App, var repository: UserUpdate.Repository) :
    MeowViewModel(app) {

    var eventLiveData = SingleLiveData<ApiEvent<*>>()
    var userLiveData = SingleLiveData<User>()
    var deleteEventLiveData = SingleLiveData<ApiEvent<*>>()

    fun callApi(request: UserUpdate.Api.RequestUpdate) {
        eventLiveData.postValue(ApiEvent.Loading(true))
        val response = repository.updateUserFromApi(request)
        if (response.status) {
            val user = app.dataSource.fetchUser()
            user.apply {
                username = request.username
                email = request.email
            }
            app.dataSource.saveUser(user)
            eventLiveData.postValue(ApiEvent.Success(response))
        } else {
            eventLiveData.postValue(ApiEvent.Error(response))
        }
    }

    fun deleteUserApi() {
        eventLiveData.postValue(ApiEvent.Loading(true))
        val response = repository.deleteUserFromApi()
        if (response.status) {
            app.dataSource.clear()
            deleteEventLiveData.postValue(ApiEvent.Success(response))
        } else {
            deleteEventLiveData.postValue(ApiEvent.Error(response))
        }
    }

    fun fetchData() {
        userLiveData.postValue(app.dataSource.fetchUser())
    }

}