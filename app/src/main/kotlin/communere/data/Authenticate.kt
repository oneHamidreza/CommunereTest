package communere.data

import com.auth0.android.jwt.JWT
import com.squareup.moshi.Json
import communere.Constants
import communere.di.AppApi
import meow.ktx.fromJson
import meow.ktx.isNotNullOrEmpty
import meow.ktx.logD
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

    class Repository {

        fun loginFromApi(request: Api.RequestLogin): Api.ResponseLogin {
            val username = request.username
            val password = request.password

            logD(m = "username: $username , password: $password")

            if (username == Constants.ADMIN_USERNAME && password == Constants.ADMIN_PASSWORD) {
                return """
                    {
                        "status": true,
                        "name": "Admin",
                        "username": "admin",
                        "user_type": 1,
                        "token": "SOME_TOKEN_VALUE"
                    }
                """.trimIndent().fromJson<Api.ResponseLogin>()!!
            } else if (username == Constants.DEFAULT_USER_USERNAME && password == Constants.DEFAULT_USER_PASSWORD) {
                // Simulate a login api response.
                return """
                    {
                        "status" : true,
                        "name" : "Hamidreza",
                        "family" : "Etebarian",
                        "username" : "$username",
                        "user_type" : 0,
                        "token" : "SOME_TOKEN_VALUE"
                    }
                """.trimIndent().fromJson<Api.ResponseLogin>()!!
            } else {
                return """
                    {
                        "status" : false,
                        "message":"Username or password is invalid."
                    }
                """.trimIndent().fromJson<Api.ResponseLogin>()!!
            }

        }

    }

    interface Api {

        class RequestLogin(
            @Json(name = "username") var username: String? = null,
            @Json(name = "password") var password: String? = null
        ) {
            fun validate(): Boolean {
                return username?.length ?: 0 >= 5 &&
                        password?.length ?: 0 >= 6
            }
        }

        data class ResponseLogin(
            @Json(name = "status") var status: Boolean = false,
            @Json(name = "message") var message: String? = null,
            @Json(name = "name") var name: String? = null,
            @Json(name = "family") var family: String? = null,
            @Json(name = "username") var username: String? = null,
            @Json(name = "user_type") var userTypeValue: Int = UserTypes.USER.ordinal,
            @Json(name = "token") var token: String? = null
        ) {
            val user: User
                get() {
                    return User(
                        username = username,
                        userTypeValue = userTypeValue
                    ).apply {
                        alias = buildString {
                            if (name.isNotNullOrEmpty()) {
                                append(name)
                                append(" ")
                            }
                            if (family.isNotNullOrEmpty())
                                append(family)
                        }
                    }
                }

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
            @Body request: RequestLogin
        ): ResponseLogin
    }
}