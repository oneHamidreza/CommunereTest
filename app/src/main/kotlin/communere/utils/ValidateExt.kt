package communere.utils

import meow.ktx.avoidException
import java.util.regex.Pattern

/**
 * Validate Extensions.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-22
 */

/**
 * Check validation of Email.
 * If input is empty return false.
 * @return a boolean describes validation of Email.
 */
@Suppress("RegExpRedundantEscape")
fun String?.validateEmail() = avoidException {
    if (isNullOrEmpty())
        return false
    Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE)
        .matcher(this).matches()
} ?: false

/**
 * Check validation of Password.
 * If input is empty return false.
 * @param minimum is a integer that describes minimum character count in a string.
 * @return a boolean describes validation of Password.
 */
fun String?.validatePassword() = avoidException {
    if (isNullOrEmpty())
        return false
    Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", Pattern.CASE_INSENSITIVE)
        .matcher(this).matches()
} ?: false

/**
 * Check validation of Username.
 * If input is empty return false.
 * @return a boolean describes validation of Username.
 */
fun String?.validateUsername() = avoidException {
    if (isNullOrEmpty())
        return false
    Pattern.compile("[a-z][a-z0-9_]{3,}", Pattern.CASE_INSENSITIVE).matcher(this).matches()
} ?: false