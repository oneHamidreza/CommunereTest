package communere.ui.login

import communere.App
import communere.data.Authenticate
import meow.core.api.MeowEvent
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

    var eventLiveData = SingleLiveData<MeowEvent<*>>()
    var modelLiveData = SingleLiveData<Authenticate.Api.ResponseGetToken>()

    fun callApi(request: Authenticate.Api.RequestGetToken) {
        safeCallApi(
            liveData = eventLiveData,
            apiAction = { repository.getTokenFromApi(request) }
        ) { _, it ->
            val user = it?.decodeJWT()
            user?.let {
                app.dataSource.saveUser(it)
            }
            app.dataSource.saveApiToken(it?.token ?: "")

            modelLiveData.postValue(it)
        }//todo handle 401 in Meow Api
    }

}