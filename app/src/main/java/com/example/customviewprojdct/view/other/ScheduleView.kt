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
 * TODO：
 * 1.稳定性保证（区间排重和排序）
 * 2.保持两位小数计算？
 * 3.优化绘制代码（减少onDraw中循环）
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

    //日程区间
    private var drawScheduleRange = arrayListOf<Triple<Boolean, Float,Float>>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        reMeasureRect()
    }

    private fun reMeasureRect() {
        val length = scheduleRange.last - scheduleRange.first
        rectWidth = (width - (2 * offsetX)) / (length)
        rectHeight = height - textHeight
        Log.i(
            "baize_",
            "width:$width cWidth:$rectWidth length:$length offsetX:${offsetX} textHeight:${textHeight} rectHeight:${rectHeight}"
        )
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

    private val drawRange = arrayListOf<Triple<Boolean, Float, Float>>()

    private fun measureDrawRange() {
        drawScheduleRange.forEach {
            var startPoint = 0f
            var overWidth = 0f

            val wd = it.third - it.second
            val count = (wd / 1).toInt()
            val diff = wd % 1
            overWidth = count * rectWidth + diff * rectWidth  //TODO：0.5进1
            Log.i("baize_", "count:$count diff:$diff drawWidth:$overWidth  rectWidth:$$rectWidth")
            //确定起点
            val sd = it.second - scheduleRange.first
            val countS = (sd / 1).toInt()
            val diffS = (sd % 1)
            startPoint = offsetX + (countS * rectWidth + diffS * rectWidth)
            Log.i("baize_", "countS:$countS diffS:$diffS drawStart:${startPoint}")

            drawRange.add(Triple(it.first, startPoint, overWidth))
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(dashPath, bgPaint)

        scheduleRange.forEachIndexed { index, e ->
            scalePaint.color = Color.BLACK
            drawScheduleRange.forEach {
                if (it.first && e.toFloat() in it.second..it.third) {
                    scalePaint.color = Color.RED
                    Log.i("baize_", "text red:${e}")
                }
            }
            canvas.drawText(
                "$e",
                (offsetX + index * rectWidth),
                rectHeight + textHeight - 3,
                scalePaint
            )
        }
        drawRange.forEach {
            rangePaint.color = if (it.first) Color.RED else Color.GRAY
            Log.i("baize_", "drawRange:${it}")
            canvas.drawRect(it.second, 0F, it.second + it.third, rectHeight + 1.dp / 2, rangePaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val length = scheduleRange.last - scheduleRange.first
        val dW = rectWidth * length + offsetX * 2 + 10.dp
        val width = resolveSize(dW.toInt(), widthMeasureSpec)
        val measureHeight = ((width - (2 * offsetX)) / (length)) + textHeight
        val height = resolveSize(measureHeight.toInt(), heightMeasureSpec)
        Log.i(
            "baize_",
            "measure rectHeight:${rectHeight} textHeight:${textHeight} total:${rectHeight + textHeight}"
        )
        setMeasuredDimension(width, height)
    }

    fun clearSchedule() {
        drawScheduleRange.clear()
    }

    fun addMySchedule(start: Float, end: Float) {
//        if (!start in scheduleRange || !end in scheduleRange) return
        drawScheduleRange.add(Triple(true, start, end))
        //区间排重和排序
        invalidate()
    }

    fun addOtherSchedule(start: Float, end: Float) {
//        if (!start in scheduleRange || !end in scheduleRange) return
        drawScheduleRange.add(Triple(false, start, end))
        //区间排重和排序
        invalidate()
    }

    fun notifyChange() {
        //TODO 区间排重和排序
        measureDrawRange()
        invalidate()
    }

}