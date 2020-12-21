package communere.ui.base

import androidx.databinding.ViewDataBinding
import meow.core.ui.MeowDialogFragment

/**
 * Base Dialog Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

abstract class BaseDialogFragment<B : ViewDataBinding> : MeowDialogFragment<B>()