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
 * 1.稳定性保证（区间排重和排序）
 * 2.保持两位小数计算？
 */
class ScheduleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0
) : View(context, attrs, d) {
    private val scheduleRange = 7..23

    private var rectWidth = 20.dp
    private var rectHeight = 20.dp
    private var offsetX = 6.dp  //进度条左右间隔（紧贴边会导致左右两侧文字显示不全）

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

    //我的日程区间
    private var myDrawScheduleRange = arrayListOf<Pair<Float, Float>>()  //first：开始点，second：结束点

    //其他人的日程
    private var otherDrawScheduleRange = arrayListOf<Pair<Float, Float>>()

    //存储测量后的日程（真正绘制范围）
    private val drawRange = arrayListOf<Triple<Boolean, Float, Float>>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        reMeasureBgRect()
        reMeasureSchedule()
    }

    private fun reMeasureBgRect() {
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


    private fun reMeasureSchedule() {
        drawRange.clear()
        measureDrawRange(myDrawScheduleRange, true)
        measureDrawRange(otherDrawScheduleRange, false)
    }

    private fun measureDrawRange(ranges: ArrayList<Pair<Float, Float>>, isMy: Boolean) {
        val maxWidth = width - offsetX
        ranges.forEach {
            //确定起点
            val sd = it.first - scheduleRange.first
            val countS = (sd / 1).toInt()
            val diffS = (sd % 1)
            val startPoint = offsetX + (countS * rectWidth + diffS * rectWidth)
            Log.i("baize_", "countS:$countS diffS:$diffS drawStart:${startPoint}")

            //确定长度
            val wd = it.second - it.first
            val count = (wd / 1).toInt()
            val diff = wd % 1
            var overWidth = count * rectWidth + diff * rectWidth  //TODO：可做0.5进1
            if (startPoint + overWidth > maxWidth) {  //超过区间范围修正
                overWidth = maxWidth - startPoint
            }
            Log.i("baize_", "count:$count diff:$diff drawWidth:$overWidth  rectWidth:$$rectWidth")

            drawRange.add(Triple(isMy, startPoint, overWidth))
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(dashPath, bgPaint)

        scheduleRange.forEachIndexed { index, e ->
            scalePaint.color = Color.BLACK
            for (i in 0 until myDrawScheduleRange.size) {
                if (e.toFloat() in myDrawScheduleRange[i].first..myDrawScheduleRange[i].second) {
                    scalePaint.color = Color.RED
                    Log.i("baize_", "text red:${e}")
                    break
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
        myDrawScheduleRange.clear()
        otherDrawScheduleRange.clear()
    }

    fun addMySchedule(start: Float, end: Float) {
        myDrawScheduleRange.add(Pair(start, end))
    }

    fun addOtherSchedule(start: Float, end: Float) {
        otherDrawScheduleRange.add(Pair(start, end))
    }

    fun notifyChange() {
        reMeasureSchedule()
        invalidate()
    }

}