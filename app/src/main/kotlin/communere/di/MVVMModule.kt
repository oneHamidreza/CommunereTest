package communere.di

import meow.ktx.bindAutoTag
import org.kodein.di.Kodein.Module
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import communere.data.Authenticate
import communere.ui.home.HomeViewModel
import communere.ui.login.LoginViewModel
import communere.ui.main.MainViewModel

/**
 * The Module of MVVM (ViewModels, Repositories).
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

val mvvmModule = Module("MVVM Module", false) {

    bindAutoTag<HomeViewModel>() with singleton {
        HomeViewModel(kodein.direct.instance())
    }
    bindAutoTag<LoginViewModel>() with provider {
        LoginViewModel(
            kodein.direct.instance(),
            instance()
        )
    }
    bindAutoTag<MainViewModel>() with provider {
        MainViewModel(kodein.direct.instance())
    }

    bind() from provider { Authenticate.Repository(instance()) }

}