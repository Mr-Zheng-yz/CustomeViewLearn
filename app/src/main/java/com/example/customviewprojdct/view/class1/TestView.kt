package com.example.customviewprojdct.view.class1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp

val RADIUS = 100.dp
class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0
) : View(context, attrs,0) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    }
    private val path = Path()
    lateinit var pathMeasure: PathMeasure

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addRect(
            width / 2f - RADIUS,
            height / 2f,
            width / 2f + RADIUS,
            height / 2f + RADIUS * 2,
            Path.Direction.CW
        )
//        path.fillType = Path.FillType.EVEN_ODD
        pathMeasure = PathMeasure(path, false)
//        pathMeasure.length  // 返回Paht长度
//        pathMeasure.getPosTan() //返回一个正切值
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }
}