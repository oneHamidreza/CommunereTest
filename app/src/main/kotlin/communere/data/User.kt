package communere.data

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
) {
    val userType: UserTypes
        get() {
            return UserTypes.values().find { it.ordinal == userTypeValue } ?: UserTypes.USER
        }

    class Repository {

        fun getAllUsers(): List<User> {
            val list = arrayListOf<User>()
            (1..30).forEach {
                list.add(User(alias = "User #$it"))
            }
            return list
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.alias == newItem.alias

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }
}

enum class UserTypes {
    ADMIN, USER
}