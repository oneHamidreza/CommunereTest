package communere.ui.base

import androidx.databinding.ViewDataBinding
import meow.core.ui.MeowBottomSheetDialogFragment

/**
 * Base Bottom Sheet Dialog Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding> : MeowBottomSheetDialogFragment<B>()