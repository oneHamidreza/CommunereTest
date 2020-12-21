package communere.widget.navheader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import communere.R
import communere.databinding.NavHeaderViewBinding
import meow.ktx.dp
import meow.ktx.getStatusBarHeight

/**
 * Navigation Header View class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class NavHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs, defStyleAttrs) {

    private val binding = DataBindingUtil.inflate<NavHeaderViewBinding>(
        LayoutInflater.from(context),
        R.layout.nav_header_view,
        this,
        true
    )

    var avatarUrl: String? = ""
        set(value) {
            field = value
            if (value == null)
                return
            binding.avatarUrl = value
        }
    var alias: String? = ""
        set(value) {
            field = value
            if (value == null)
                return
            binding.alias = value
        }
    var caption: String? = ""
        set(value) {
            field = value
            if (value == null)
                return
            binding.caption = value
        }

    init {
        orientation = VERTICAL
        setPaddingRelative(16.dp(), context.getStatusBarHeight() + 16.dp(), 16.dp(), 16.dp())
    }

}