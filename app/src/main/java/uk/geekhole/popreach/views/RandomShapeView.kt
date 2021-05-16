package uk.geekhole.popreach.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import uk.geekhole.popreach.models.Shape

class RandomShapeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var gestureDetector: GestureDetectorCompat? = null

    // We make a reference to these outside of the onDraw method as creating objects in onDraw is bad
    private var shapeDrawables = listOf<Drawable?>()
        set(value) {
            field = value
            // Make sure that whenever the shapes are set we invalidate to redraw everything
            invalidate()
        }

    fun setShapeData(shapes: List<Shape>) {
        shapeDrawables = shapes.map {
            val drawable = ResourcesCompat.getDrawable(resources, it.type.resourceId, null)
            drawable?.setBounds(it.startX, it.startY, it.endX, it.endY)
            drawable
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        shapeDrawables.forEach { it?.draw(canvas) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector?.onTouchEvent(event) == true) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

}