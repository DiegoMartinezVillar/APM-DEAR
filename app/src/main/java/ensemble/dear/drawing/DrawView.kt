package ensemble.dear.drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import ensemble.dear.R

class DrawView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private val paint: Paint = Paint()
    private val path: Path = Path()

    init {
        paint.isAntiAlias = true
        paint.strokeWidth = 7f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
    }

    fun clear() {
        path.reset()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)

        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.primaryColor)
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
            MotionEvent.ACTION_UP -> performClick()
            else -> return false
        }
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

}
