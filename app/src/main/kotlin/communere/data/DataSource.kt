package communere.data

import communere.App
import communere.di.AppApi
import meow.core.arch.DataSourceInterface
import meow.core.data.MeowSharedPreferences
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.erased.instance

/**
 * Data Source class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class DataSource(override var app: App) : DataSourceInterface, KodeinAware {

    override val kodein by closestKodein(app)
    private val api: AppApi by instance<AppApi>()
    private val spMain: MeowSharedPreferences by instance<MeowSharedPreferences>("spMain")
    private val spUpdate: MeowSharedPreferences by instance<MeowSharedPreferences>("spUpdate")

    suspend fun getTokenFromApi(request: Authenticate.Api.RequestGetToken) =
        api.createServiceByAdapter<Authenticate.Api>().getToken(request)

    fun isLogin() = fetchApiToken().isNotEmpty()
    fun fetchUser() = spMain.get("user", User())
    fun saveUser(it: User) = spMain.put("user", it)

    fun fetchApiToken() = spMain.get("apiToken", "")
    fun saveApiToken(it: String) = spMain.put("apiToken", it)

    fun fetchApiRefreshToken() = spMain.get("apiRefreshToken", "")
    fun saveApiRefreshToken(it: String) = spMain.put("apiRefreshToken", it)

    fun clear() {
        saveApiToken("")
        saveApiRefreshToken("")
        saveUser(User())
    }

}