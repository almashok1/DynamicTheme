package kz.adamant.dynamictheme.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import kz.adamant.dynamictheme.Theme
import kz.adamant.dynamictheme.ThemeManager

class Button
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
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
        setTextColor(
            ContextCompat.getColor(
                context,
                theme.buttonTheme.textColor
            )
        )
        backgroundTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    theme.buttonTheme.backgroundTint
                )
            )
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}