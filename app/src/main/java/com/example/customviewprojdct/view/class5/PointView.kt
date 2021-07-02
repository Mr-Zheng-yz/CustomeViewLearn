package com.example.customviewprojdct.view.class5

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp

class PointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var point: PointF = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoint(point.x, point.y, paint)
    }

}

//class5：自定义动画计算器，让属性动画支持各种类型
class PointFEvaluator : TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val startX = startValue.x
        val endX = endValue.x
        val currentX = startX + (endX - startX) * fraction
        val startY = startValue.y
        val endY = endValue.y
        val currentY = startY + (endY - startY) * fraction
        return PointF(currentX,currentY)
    }
}