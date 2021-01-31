package kz.adamant.dynamictheme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kz.adamant.dynamictheme.Theme
import kz.adamant.dynamictheme.ThemeManager

class Toolbar
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }
    override fun onThemeChanged(theme: Theme) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.toolbarTheme.backgroundColor
            )
        )
    }
}