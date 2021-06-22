package com.example.customviewprojdct.view.class3

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp
import kotlin.math.cos
import kotlin.math.sin

class SportView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    enum class DrawTextModel(model: Int) {
        STATIC_MODE(0),
        DYNAMIC_MODE(1)
    }

    companion object {
        private val RING_WIDTH = 20.dp
        private val RADIUS = 150.dp
        private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
        private val CIRCLELIGHT_COLOR = Color.parseColor("#FF4081")
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    private var drawText = "abab"
    private var drawTextModel = DrawTextModel.STATIC_MODE
    private var isCustomFont = false

    fun setDrawText(text: String) {
        this.drawText = text
        invalidate()
    }

    fun setDrawTextModel(model: DrawTextModel) {
        this.drawTextModel = model
        invalidate()
    }

    fun setIsCustomerTextFont(custom: Boolean) {
        this.isCustomFont = custom
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = 100.dp
        if (isCustomFont) {
            paint.typeface = ResourcesCompat.getFont(context, R.font.gameover)
        } else {
            paint.typeface = null
        }

        //绘制背景圆
        paint.color = CIRCLE_COLOR
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        //绘制进度条
        paint.color = CIRCLELIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        //画文字
        paint.textAlign = Paint.Align.CENTER    //横向居中
        paint.style = Paint.Style.FILL
        //方法一：绝对中间位置，适合静态文字
        paint.getTextBounds(drawText, 0, drawText.length, bounds)
        Log.i("yanze_text","bounds:${bounds.toString()}")
        //方法二：文字核心的中间位置，适合动态文字（不会大幅度跳动）
        paint.getFontMetrics(fontMetrics)
        Log.i("yanze_text","fontMetrics top:${fontMetrics.top} bottom:${fontMetrics.bottom} ascent:${fontMetrics.ascent} descent:${fontMetrics.descent} leading:${fontMetrics.leading}")
        val offsetY: Float = when (drawTextModel) {
            DrawTextModel.STATIC_MODE -> ((bounds.top + bounds.bottom) / 2f)
            DrawTextModel.DYNAMIC_MODE -> ((fontMetrics.ascent + fontMetrics.descent) / 2)
        }
        val offsetX : Float = (bounds.left + bounds.right) / 2f
        Log.i("yanze_text","original baseline:${height / 2f}")
        Log.i("yanze_text","offsetY:${offsetY}")
        canvas.drawText(
            drawText,
            width / 2f,
            height / 2f - offsetY,  //减去偏移，偏移本身有正负，直接减
            paint
        )

        //画辅助线
        paint.reset()
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)
        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2.dp
        val offsetLineY = height / 2f
        //5条基准线
        paint.color = Color.BLACK
        val baseline = height / 2f - offsetY
        canvas.drawLine(
            12.dp,
            baseline,
            width - 12.dp,
            baseline,
            paint
        )
        paint.color = Color.BLUE
        val ascentLine = fontMetrics.ascent + baseline
        val descent = fontMetrics.descent + baseline
        drawLine(canvas,ascentLine)
        drawLine(canvas,descent)
        paint.color = Color.GREEN
        paint.strokeWidth = 1.dp
        val topLine = fontMetrics.top + baseline
        val bottomLine = fontMetrics.bottom + baseline
        drawLine(canvas,topLine)
        drawLine(canvas,bottomLine)
        //bounds
        paint.color = Color.RED
        canvas.drawRect(
            width / 2f - offsetX,
            bounds.top + offsetLineY - offsetY,
            width / 2f + offsetX,
            bounds.bottom + offsetLineY - offsetY,
            paint
        )

        //文字贴边：顶边
//        paint.textSize = 100.dp
//        paint.color = CIRCLELIGHT_COLOR
//        paint.style = Paint.Style.FILL
//        paint.textAlign = Paint.Align.LEFT
//        canvas.drawText("abab",-bounds.left.toFloat(), (-bounds.top).toFloat(),paint)
        //文字贴边：左边
//        paint.textSize = 15.dp
//        paint.getTextBounds("abab",0,"abab".length,bounds)
//        canvas.drawText("abab", 0f, (-bounds.top).toFloat(),paint)
    }

    private fun drawLine(canvas: Canvas,y: Float) {
        canvas.drawLine(
            12.dp,
            y,
            width - 12.dp,
            y,
            paint
        )
    }

}