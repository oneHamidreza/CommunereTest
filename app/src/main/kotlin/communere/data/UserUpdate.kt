package communere.data

import com.squareup.moshi.Json
import communere.utils.validateEmail
import communere.utils.validateUsername
import meow.ktx.fromJson
import meow.ktx.logD

/**
 * User Update class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class UserUpdate {

    class Repository {

        fun updateUserFromApi(request: Api.RequestUpdate): Api.ResponseUpdate {
            val username = request.username
            val email = request.email

            logD(m = "username: $username , email: $email")
            return """
                    {
                        "status": true,
                        "message": "User has been updated."
                    }
                """.trimIndent().fromJson<Api.ResponseUpdate>()!!
        }

        fun deleteUserFromApi(): Api.ResponseDelete {
            return """
                    {
                        "status": true,
                        "message":"User has been deleted."
                    }
                """.trimIndent().fromJson<Api.ResponseDelete>()!!
        }

    }

    interface Api {

        class RequestUpdate(
            @Json(name = "username") var username: String? = null,
            @Json(name = "email") var email: String? = null
        ) {
            fun validate(): Boolean {
                return username.validateUsername() &&
                        email.validateEmail()
            }
        }

        data class ResponseUpdate(
            @Json(name = "status") var status: Boolean = false,
            @Json(name = "message") var message: String? = null,
        )

        data class ResponseDelete(
            @Json(name = "status") var status: Boolean = false,
            @Json(name = "message") var message: String? = null,
        )
    }
}