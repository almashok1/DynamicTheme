package kz.adamant.dynamictheme

import android.R.attr.startX
import android.R.attr.startY
import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.isVisible
import kz.adamant.dynamictheme.views.Button
import kotlin.math.hypot


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var container: ViewGroup
    private lateinit var innerContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(
            LayoutInflater.from(this),
            MyLayoutInflater(delegate)
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        imageView = findViewById(R.id.imageView)
        container = findViewById(R.id.container)
        innerContainer = findViewById(R.id.inner_container)
        findViewById<Button>(R.id.button).setOnTouchListener { view, motionEvent ->
            view.performClick()
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val x = motionEvent.x + innerContainer.paddingStart
                val y = motionEvent.y + innerContainer.measuredHeight
                val p = Point(x.toInt(), y.toInt())
                if (ThemeManager.theme == Theme.DARK) {
                    setTheme(Theme.LIGHT, p)
                } else if (ThemeManager.theme == Theme.LIGHT) {
                    setTheme(Theme.DARK, p)
                }
            }
            false

        }
    }

    override fun onPostResume() {
        super.onPostResume()
        setTheme(Theme.LIGHT, animate = false)
    }


    private fun getCenterPointOfView(view: View): Point {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x: Int = location[0] + view.width / 2
        val y: Int = location[1] + view.height / 2
        return Point(x, y)
    }

    private fun setTheme(theme: Theme, point: Point? = null, animate: Boolean = true) {
        if (!animate) {
            ThemeManager.theme = theme
            return
        }
        if (imageView.isVisible) return

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        container.draw(canvas)

        imageView.setImageBitmap(bitmap)
        imageView.isVisible = true

        ThemeManager.theme = theme

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        val center = point ?: getCenterPointOfView(container)

        val anim = ViewAnimationUtils.createCircularReveal(
            container,
            center.x,
            center.y,
            0f,
            finalRadius
        )
        anim.duration = 700L
        anim.doOnEnd {
            imageView.setImageDrawable(null)
            imageView.isVisible = false
        }
        anim.start()
    }
}

