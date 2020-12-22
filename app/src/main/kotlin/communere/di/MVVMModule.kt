package communere.di

import communere.data.Authenticate
import communere.data.User
import communere.data.UserUpdate
import communere.ui.home.HomeViewModel
import communere.ui.login.LoginViewModel
import communere.ui.main.MainViewModel
import communere.ui.register.RegisterViewModel
import communere.ui.userIndex.UserIndexViewModel
import communere.ui.userUpdate.UserUpdateViewModel
import meow.ktx.bindAutoTag
import org.kodein.di.Kodein.Module
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

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
    bindAutoTag<RegisterViewModel>() with provider {
        RegisterViewModel(
            kodein.direct.instance(),
            instance()
        )
    }
    bindAutoTag<MainViewModel>() with provider {
        MainViewModel(kodein.direct.instance())
    }
    bindAutoTag<UserIndexViewModel>() with provider {
        UserIndexViewModel(kodein.direct.instance(), instance())
    }
    bindAutoTag<UserUpdateViewModel>() with provider {
        UserUpdateViewModel(kodein.direct.instance(), instance())
    }

    bind() from provider { Authenticate.Repository() }
    bind() from provider { User.Repository() }
    bind() from provider { UserUpdate.Repository() }

}