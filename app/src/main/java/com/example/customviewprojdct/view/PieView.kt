package com.example.customviewprojdct.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp
import kotlin.math.cos
import kotlin.math.sin

class PieView(context: Context, attribute: AttributeSet) : View(context, attribute) {
    companion object {
        val RADIUS = 150.dp
        val OFFSET_LENGTH = 50f
        val OFFSET_INDEX = 2
        val ANGLE = floatArrayOf(60f, 90f, 150f, 60f)
        val COLOR = listOf(
            Color.parseColor("#DC143C")
            , Color.parseColor("#00BFFF")
            , Color.parseColor("#FFFF00")
            , Color.parseColor("#7CFC00")
        )
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun onDraw(canvas: Canvas) {
        var startAngle = 0f
        for ((index, angle) in ANGLE.withIndex()) {
            if (index == OFFSET_INDEX) {
                canvas.save()
                canvas.translate(
                    (OFFSET_LENGTH * cos(Math.toRadians(startAngle + (angle / 2).toDouble()))).toFloat(),
                    (OFFSET_LENGTH * sin(Math.toRadians(startAngle + (angle / 2).toDouble()))).toFloat()
                )
            }
            paint.color = COLOR[index]
            canvas.drawArc(
                width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS, height / 2 + RADIUS
                , startAngle, angle, true, paint
            )
            startAngle += angle
            if (index == OFFSET_INDEX) {
                canvas.restore()
            }
        }
    }

}