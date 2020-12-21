package communere.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import communere.R
import meow.core.ui.MeowActivity
import meow.ktx.getColorCompat
import meow.ktx.updateNavigationBarColorByTheme
import meow.ktx.updateStatusBarByTheme

/**
 * Base Activity class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

abstract class BaseActivity<B : ViewDataBinding> : MeowActivity<B>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateStatusBarByTheme()
        updateNavigationBarColorByTheme(getColorCompat(R.color.meow_nav_bar))
    }
}