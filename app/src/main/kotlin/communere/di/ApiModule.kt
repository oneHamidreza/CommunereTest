package communere.di

import communere.App
import communere.data.DataSource
import meow.controller
import meow.core.api.InterceptorBlock
import meow.core.api.MeowApi
import meow.core.api.MeowOauthToken
import meow.core.api.addInterceptorBlocks
import okhttp3.OkHttpClient
import org.kodein.di.Kodein.Module
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * The Module of Api.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

val apiModule = Module("Api Module", false) {
    bind() from provider {
        AppApi(
            app = instance(),
            dataSource = instance()
        )
    }
}

open class AppApi(
    open val app: App,
    open val dataSource: DataSource,
    override var baseUrl: String = URL_API,
    override var options: Options = Options()
) : MeowApi(baseUrl = baseUrl, options = options) {

    companion object{
        const val URL_HOST = "http://communere.app"
        const val URL_API= "$URL_HOST/api/"

        fun getImageUrl(fileName:String?) = "$URL_HOST/$fileName"
    }

    override fun getRefreshTokenResponse(): Response<MeowOauthToken>? {
        val refreshToken = dataSource.fetchApiRefreshToken()
        if (refreshToken.isEmpty()) return null

        val request = MeowOauthToken.RequestRefreshToken(refreshToken, controller.meowSession)
        return Response.success(createServiceByAdapter<Oauth>().refreshToken(request))
    }

    override fun getOKHttpClientBuilder(): OkHttpClient.Builder {
        return super.getOKHttpClientBuilder().apply {
            val isLogin = dataSource.isLogin()
            val authorization = Authorization.Bearer(isLogin, dataSource.fetchApiToken())

            val interceptorBlocks: List<InterceptorBlock> = listOf(
                getCacheInterceptorBlock(app, options),
                authorization.interceptorBlock,
                {
                    it.header("User-Agent", getDefaultUserAgent(app))
                    it.header("Content-Type","application/json")
                    it.header("Accept","application/json")
                }
            )
            addInterceptorBlocks(interceptorBlocks)

            authenticator(OauthRefreshToken(this@AppApi, authorization))
        }
    }

    interface Oauth {
        @POST("/oauth2/token")
        fun refreshToken(
            @Body request: MeowOauthToken.RequestRefreshToken
        ): MeowOauthToken
    }
}

class TestAppApi(
    override var app: App,
    override var dataSource: DataSource,
    override var baseUrl: String = "test-api.yoururl.com",
    override var options: Options = Options()
) : AppApi(app, dataSource, baseUrl, options)