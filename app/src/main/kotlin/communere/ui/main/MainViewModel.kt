package communere.ui.main

import android.view.View
import communere.App
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * Main View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class MainViewModel(override var app:App) : MeowViewModel(app) {

    val logoutLiveData = SingleLiveData<Boolean>()

    fun logout() {
        app.dataSource.clear()
        logoutLiveData.postValue(true)
    }

    fun onClickedLogout(@Suppress("UNUSED_PARAMETER") view: View) {
        logout()
    }

}