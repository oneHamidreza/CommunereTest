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
    @Json(name = "avatarUrl") var avatarUrl: String? = null
){
    val isValid: Boolean
    get() = username.isNotNullOrEmpty() &&
            password.isNotNullOrEmpty() &&
            userId.isNotNullOrEmpty()
}