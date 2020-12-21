package communere

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import meow.MeowApp
import meow.MeowController
import meow.bindMeow
import meow.ktx.isNightModeFromSettings
import meow.meowModule
import org.kodein.di.Kodein
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import communere.data.DataSource
import communere.di.*

/**
 * Application class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class App : MeowApp() {

    override val kodein = Kodein.lazy {
        bind() from singleton { kodein.direct }
        bind() from singleton { this@App }
        import(androidXModule(this@App))
        import(meowModule)
        import(appModule)
        import(apiModule)
        import(mvvmModule)
    }

    val dataSource by instance<DataSource>()

    override fun getLanguage(context: Context?) = "en"
    override fun getTheme(context: Context?) =
        if (context.isNightModeFromSettings()) MeowController.Theme.NIGHT else MeowController.Theme.DAY

    override fun onCreate() {
        super.onCreate()
        bindMeow {
            it.isDebugMode = BuildConfig.DEBUG
            it.isLogTagNative = false

            it.currency = MeowController.Currency.USD
            it.calendar = MeowController.Calendar.GEORGIAN

            it.defaultTypefaceResId = R.font.roboto_regular
            it.toastTypefaceResId = R.font.roboto_regular

            it.onException = {
                // Log to Fabric or any other Crash Management System. Just use `avoidException` instead of `try{}catch{}`
            }
        }

    }

}

@GlideModule
class AppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.ERROR)
    }
}
