package communere.ui.login

import communere.App
import communere.data.ApiEvent
import communere.data.Authenticate
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * Login View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class LoginViewModel(override var app:App, var repository: Authenticate.Repository) : MeowViewModel(app){

    var eventLiveData = SingleLiveData<ApiEvent<*>>()

    fun callApi(request: Authenticate.Api.RequestLogin) {
        eventLiveData.postValue(ApiEvent.Loading(true))
        val response = repository.loginFromApi(request)
        if (response.status){
            val user = response.user
            app.dataSource.saveUser(user)
            eventLiveData.postValue(ApiEvent.Success(user))
        }else{
            eventLiveData.postValue(ApiEvent.Error(response))
        }
    }

}