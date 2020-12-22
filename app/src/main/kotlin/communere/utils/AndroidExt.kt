package communere.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Android Extensions class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-22
 */

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}