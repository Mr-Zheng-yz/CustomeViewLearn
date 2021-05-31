package com.example.customviewprojdct.view.class1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp
import kotlin.math.cos
import kotlin.math.sin

class DashboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    companion object {
        private val RADIUS = 150.dp
        private val OPEN_ANGLE = 120  //仪表盘底部开口角度
        private val DASH_WIDTH = 2.dp
        private val DASH_LENGTH = 10.dp
        private val LENGTH = 120.dp

        private val SCALE = 8     // 刻度
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3.dp
        style = Paint.Style.STROKE
    }

    private val dash = Path()
    private val arcPath = Path()
    private lateinit var dashEffect: PathDashPathEffect

    init {
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        arcPath.reset()
        arcPath.addArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360f - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(arcPath, false)
        dashEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - DASH_WIDTH) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画弧
        canvas.drawPath(arcPath, paint)

        // 画刻度
        paint.pathEffect = dashEffect
        canvas.drawPath(arcPath, paint)
        paint.pathEffect = null

        //画指针
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LENGTH * cos(Math.toRadians(((90 + OPEN_ANGLE / 2) + (360 - OPEN_ANGLE) / 20 * SCALE).toDouble())).toFloat(),
            height / 2f + LENGTH * sin(Math.toRadians(((90 + OPEN_ANGLE / 2) + (360 - OPEN_ANGLE) / 20 * SCALE).toDouble())).toFloat(),
            paint
        )
    }

}