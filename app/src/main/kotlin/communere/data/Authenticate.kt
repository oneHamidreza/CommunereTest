package communere.data

import com.auth0.android.jwt.JWT
import com.squareup.moshi.Json
import communere.di.AppApi
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Authenticate class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class Authenticate {

    class Repository(var dataSource: DataSource) {

        suspend fun getTokenFromApi(request: Api.RequestGetToken) = dataSource.getTokenFromApi(request)

    }

    interface Api {

        class RequestGetToken(
            @Json(name = "username") var username: String? = null,
            @Json(name = "password") var password: String? = null
        )

        class ResponseGetToken(
            @Json(name = "token") var token: String? = null
        ){
            fun decodeJWT(): User {
                val jwt = JWT(token ?: "")
                val name = jwt.getClaim("name").asString()
                val username = jwt.getClaim("username").asString()
                val avatarUrl = AppApi.getImageUrl(jwt.getClaim("avatar").asString())
                return User(alias = name, username = username, avatarUrl = avatarUrl)
            }
        }

        @POST("authentication_token")
        suspend fun getToken(
            @Body request: RequestGetToken
        ): ResponseGetToken
    }
}