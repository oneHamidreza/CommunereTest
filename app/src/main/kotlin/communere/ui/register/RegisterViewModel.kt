package communere.ui.register

import communere.App
import communere.data.ApiEvent
import communere.data.Authenticate
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * Register View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class RegisterViewModel(override var app: App, var repository: Authenticate.Repository) :
    MeowViewModel(app) {

    var eventLiveData = SingleLiveData<ApiEvent<*>>()

    fun callApi(request: Authenticate.Api.RequestRegister) {
        eventLiveData.postValue(ApiEvent.Loading(true))
        val response = repository.registerFromApi(request)
        if (response.status) {
            val user = response.user
            app.dataSource.saveUser(user)
            eventLiveData.postValue(ApiEvent.Success(user))
        } else {
            eventLiveData.postValue(ApiEvent.Error(response))
        }
    }

}