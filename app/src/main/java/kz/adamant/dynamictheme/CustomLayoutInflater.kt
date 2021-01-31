package kz.adamant.dynamictheme

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kz.adamant.dynamictheme.views.Button
import kz.adamant.dynamictheme.views.LinearLayout
import kz.adamant.dynamictheme.views.TextView
import kz.adamant.dynamictheme.views.Toolbar

class MyLayoutInflater(
    private val delegate: AppCompatDelegate
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return when (name) {
            "TextView" -> TextView(context, attrs)
            "LinearLayout" -> LinearLayout(context, attrs)
            "Button" -> Button(context, attrs)
            "Toolbar" -> Toolbar(context, attrs)
            else -> delegate.createView(parent, name, context, attrs)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null, name, context, attrs)
    }
}