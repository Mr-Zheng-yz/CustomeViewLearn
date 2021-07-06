package com.example.customviewprojdct.view.class11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart

/**
 * 支持多手指触摸View🤌
 * 2：配合型
 */
private val IMAGE_SIZE = 200.dp

class MultiTouchView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bitmap = getAvatart(resources, IMAGE_SIZE.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var downPoint = PointF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var sumX = 0f
        var sumY = 0f
        val ifPointerUp = MotionEvent.ACTION_POINTER_UP == event.actionMasked
        for (i in 0 until event.pointerCount) {
            //如果是POINTER_UP，并且是抬起的点，忽略
            if (ifPointerUp && i == event.actionIndex) {
                continue
            }
            sumX += event.getX(i)
            sumY += event.getY(i)
        }
        //如果是抬起事件，主动移除一个手指
        val focusX = if (!ifPointerUp) sumX / event.pointerCount else sumX / (event.pointerCount - 1)
        val focusY = if (!ifPointerUp) sumY / event.pointerCount else sumY / (event.pointerCount - 1)
        Log.i("yanze","${focusX} ${focusY}")
        when (event.actionMasked) {
            //如果非最后一根手指抬起，如果不主动移除，那么坐标偏移还会按原本手势数计算，导致图片跟手指移动时"跳一下"
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_POINTER_UP -> {
                downPoint.x = focusX
                downPoint.y = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downPoint.x + originalOffsetX
                offsetY = focusY - downPoint.y + originalOffsetY
                invalidate()
            }
        }
        return true
    }

}