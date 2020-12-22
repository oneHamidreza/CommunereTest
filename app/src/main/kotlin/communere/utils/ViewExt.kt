package communere.utils

import meow.widget.MeowTextField

/**
 * View Extensions class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-22
 */

fun MeowTextField.setEnabledWithAlpha(isEnabled: Boolean) {
    this.isEnabled = isEnabled
    getEditText()?.isEnabled = isEnabled
    this.alpha = if (isEnabled) 1.0f else 0.8f
}