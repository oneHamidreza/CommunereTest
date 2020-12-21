package communere.di

import communere.BuildConfig
import communere.data.DataSource
import meow.core.data.MeowSharedPreferences
import org.kodein.di.Kodein.Module
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

/**
 * The Module of application (resources, shared preferences).
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

val appModule = Module("App Module", false) {
    bind() from singleton { DataSource(instance()) }
    bind("spMain") from singleton {
        MeowSharedPreferences(
            instance(),
            "mainSettings_v1"
        )
    }
    bind("spUpdate") from singleton {
        MeowSharedPreferences(
            instance(),
            "updateSettings_v" + BuildConfig.VERSION_CODE
        )
    }
}