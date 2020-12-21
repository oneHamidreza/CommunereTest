package communere.ui.base

import androidx.databinding.ViewDataBinding
import meow.core.ui.MeowFragment

/**
 * Base Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

abstract class BaseFragment<B : ViewDataBinding> : MeowFragment<B>()