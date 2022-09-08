package com.example.customviewprojdct.view.other

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.util.toRange
import com.example.customviewprojdct.extensions.dp
import java.lang.Math.abs

/**
 * 日程表View
 */
class ScheduleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0
) : View(context, attrs, d) {
    private val scheduleRange = 7..23

    private var rectWidth = 20.dp
    private var rectHeight = 20.dp
    private var offsetX = 5.dp

    private val dashPath = Path()

    private val xfermodeSrcOver by lazy { PorterDuffXfermode(PorterDuff.Mode.SRC_OVER) }
    private val bgPaint = Paint().apply {
        color = Color.parseColor("#000000") //F3F3F5
        style = Paint.Style.STROKE
        strokeWidth = 1.dp
    }
    private val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 12.dp
        textAlign = Paint.Align.CENTER
    }

    private val rangePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val textHeight = scalePaint.fontMetrics.let { abs(it.ascent - it.descent) }

    //我的日程区间
    private val myScheduleRange = ArrayList<Float>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        reMeasureRect()
    }

    private fun reMeasureRect() {
        val length = scheduleRange.last - scheduleRange.first
        rectWidth = (width - (2 * offsetX)) / (length)
        rectHeight = rectWidth
        Log.i("baize_", "width:$width cWidth:$rectWidth length:$length offsetX:${offsetX} textHeight:${textHeight} rectHeight:${rectHeight}")
        Log.i(
            "baize_",
            "range ${scheduleRange.last} ${scheduleRange.step} ${scheduleRange.toRange()} ${scheduleRange}"
        )

        dashPath.reset()
        dashPath.moveTo(offsetX, 0F)
        dashPath.rLineTo(0f, rectHeight)
        for (i in 0 until length) {
            dashPath.moveTo(offsetX + i * rectWidth, 0f)
            dashPath.rLineTo(rectWidth, 0f)
            dashPath.rLineTo(0f, rectHeight)
            dashPath.rLineTo(-rectWidth, 0f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(dashPath, bgPaint)

        scheduleRange.forEachIndexed { index, i ->
            canvas.drawText("$i", (offsetX + index * rectWidth), rectHeight + textHeight - 3, scalePaint)
        }

        //确定线的起点和长度
        val start = myScheduleRange.getOrNull(0) ?: return
        scheduleRange.forEachIndexed { index, _ ->
        }
        rangePaint.color = Color.RED
        canvas.drawRect(100F, 0F, 300f, rectHeight + 1.dp / 2, rangePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val length = scheduleRange.last - scheduleRange.first
        val dW = rectWidth * length + offsetX * 2 + 10.dp
        val width = resolveSize(dW.toInt(), widthMeasureSpec)
        val height = ((width - (2 * offsetX)) / (length)) + textHeight
        Log.i("baize_", "measure rectHeight:${rectHeight} textHeight:${textHeight} total:${rectHeight + textHeight}")
        setMeasuredDimension(width, MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY))
    }

    fun setMySchedule(range: ArrayList<Float>) {
        range.sort()
        myScheduleRange.clear()
        myScheduleRange.addAll(range)
        invalidate()
    }

}