package communere.ui.main

import communere.App
import communere.data.User
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData

/**
 * Main View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class MainViewModel(override var app:App) : MeowViewModel(app){

    val userLiveData = SingleLiveData<User>()

    fun setup(){
    }

    fun logout(){
        app.dataSource.clear()
    }

}