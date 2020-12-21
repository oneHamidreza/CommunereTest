package communere.ui.home

import android.view.View
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData
import communere.App
import communere.R

/**
 * Home View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class HomeViewModel(override val app: App) : MeowViewModel(app) {

    val userInfoLiveData = SingleLiveData<String>()

    fun fetchData() {
        //todo crete UI Here
        userInfoLiveData.postValue(app.getString(R.string.userId).format(app.dataSource.fetchUser().username))
    }
}