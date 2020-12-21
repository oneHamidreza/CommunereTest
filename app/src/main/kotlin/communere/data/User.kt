package communere.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import meow.ktx.isNotNullOrEmpty

/**
 * Data User class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "alias") var alias: String? = null,
    @Json(name = "username") var username: String? = null,
    @Json(name = "password") var password: String? = null,
    @Json(name = "userId") var userId: String? = null,
    @Json(name = "avatarUrl") var avatarUrl: String? = null,
    @Json(name = "userType") var userTypeValue: Int = UserTypes.USER.ordinal,
){
    val userType: UserTypes
        get() {
            return UserTypes.values().find { it.ordinal == userTypeValue } ?: UserTypes.USER
        }
}

enum class UserTypes {
    ADMIN, USER
}