package communere.ui.main

import communere.App
import meow.core.arch.MeowViewModel

/**
 * Main View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class MainViewModel(override var app:App) : MeowViewModel(app){

    fun logout(){
        app.dataSource.clear()
    }

}