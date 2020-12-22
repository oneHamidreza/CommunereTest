package communere.data

import com.squareup.moshi.Json
import communere.Constants
import meow.ktx.fromJson
import meow.ktx.isNotNullOrEmpty
import meow.ktx.logD

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
                        "fullname": "Admin",
                        "username": "admin",
                        "email":"admin@communere.com",
                        "user_type": 0,
                        "token": "SOME_TOKEN_VALUE"
                    }
                """.trimIndent().fromJson<Api.ResponseLogin>()!!
            } else if (username == Constants.DEFAULT_USER_USERNAME && password == Constants.DEFAULT_USER_PASSWORD) {
                // Simulate a login api response.
                return """
                    {
                        "status" : true,
                        "fullname" : "Hamidreza Etebarian",
                        "username" : "$username",
                        "email":"$username@communere.com",
                        "user_type" : 1,
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

        fun registerFromApi(request: Api.RequestRegister): Api.ResponseRegister {
            return """
                    {
                        "status": true,
                        "fullname": "${request.fullname}",
                        "username": "${request.username}",
                        "user_type": 1,
                        "token": "SOME_TOKEN_VALUE"
                    }
                """.trimIndent().fromJson<Api.ResponseRegister>()!!
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
            @Json(name = "fullname") var fullname: String? = null,
            @Json(name = "username") var username: String? = null,
            @Json(name = "email") var email: String? = null,
            @Json(name = "user_type") var userTypeValue: Int = UserTypes.USER.ordinal,
            @Json(name = "token") var token: String? = null
        ) {
            val user: User
                get() = User(
                    alias = fullname ?: "",
                    username = username,
                    email = email,
                    userTypeValue = userTypeValue
                ).apply {
                    logD(m = "userType value : $userTypeValue")
                }
        }

        class RequestRegister(
            @Json(name = "fullname") var fullname: String? = null,
            @Json(name = "username") var username: String? = null,
            @Json(name = "password") var password: String? = null,
            @Json(name = "password_confirm") var passwordConfirm: String? = null
        ) {
            fun validate(): Boolean {
                return fullname.isNotNullOrEmpty() &&
                        username?.length ?: 0 >= 5 &&
                        password?.length ?: 0 >= 6 &&
                        password == passwordConfirm
            }
        }

        data class ResponseRegister(
            @Json(name = "status") var status: Boolean = false,
            @Json(name = "message") var message: String? = null,
            @Json(name = "fullname") var fullname: String? = null,
            @Json(name = "username") var username: String? = null,
            @Json(name = "email") var email: String? = null,
            @Json(name = "user_type") var userTypeValue: Int = UserTypes.USER.ordinal,
            @Json(name = "token") var token: String? = null
        ) {
            val user: User
                get() = User(
                    alias = fullname ?: "",
                    username = username,
                    email = email,
                    userTypeValue = userTypeValue,
                )
        }
    }
}