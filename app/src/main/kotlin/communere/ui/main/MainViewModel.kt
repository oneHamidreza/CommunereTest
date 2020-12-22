package communere.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import communere.App
import meow.core.arch.MeowViewModel
import meow.ktx.logD

/**
 * Main View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class MainViewModel(override var app:App) : MeowViewModel(app) {

    val logoutLiveData = MutableLiveData<Boolean>()

    fun logout() {
        app.dataSource.clear()
        logD(m = "logOut called")
        logoutLiveData.postValue(true)
    }

    fun onClickedLogout(@Suppress("UNUSED_PARAMETER") view: View) {
        logout()
    }

}