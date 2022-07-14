package com.example.customviewprojdct.view.other

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.customviewprojdct.extensions.dp

/**
 * 分段矩形的进度展示
 */
class RectProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0



) : View(context, attrs, d) {
    private var rectWidth = 28.dp
    private var maxProgress = 100F
    private var offsetX = 3.dp
    private val dashPath = Path()
    private val bound by lazy { RectF(0F, 0F, width.toFloat(), height.toFloat()) }
    private val xfermodeSrcIn by lazy { PorterDuffXfermode(PorterDuff.Mode.SRC_IN) }
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#F6335B") //F3F3F5
        style = Paint.Style.FILL
    }
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#12D9AA") //F3F3F5
        style = Paint.Style.FILL
    }

    var rectCount = 5
        set(value) {
            field = value
            reMeasureRect()
        }

    var progress = 0F
        set(value) {
            field = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        reMeasureRect()
    }

    private fun reMeasureRect() {
        rectWidth = (width / rectCount) - offsetX

        dashPath.reset()
        dashPath.moveTo(offsetX, 0F)
        dashPath.rLineTo(rectWidth, 0f)
        dashPath.rLineTo(-offsetX, height.toFloat())
        dashPath.rLineTo(-rectWidth, 0f)
        dashPath.close()

        bgPaint.pathEffect = PathDashPathEffect(dashPath, rectWidth + offsetX, 0F, PathDashPathEffect.Style.TRANSLATE)
    }

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bound, bgPaint)
        canvas.drawLine(0F, 0F, width.toFloat(), 0F, bgPaint)
        progressPaint.xfermode = xfermodeSrcIn
        val progressWidth = (progress / maxProgress) * width
        canvas.drawRect(0F, 0F, progressWidth, height.toFloat(), progressPaint)
        progressPaint.xfermode = null
        canvas.restoreToCount(count)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dW = (rectWidth + offsetX) * rectCount
        val width = resolveSize(dW.toInt(), widthMeasureSpec)
        val height = resolveSize(5.dp.toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    fun setProgressColorRes(colorRes: Int) {
        this.progressPaint.color = ContextCompat.getColor(context, colorRes)
    }

    fun setProgress(progress: Float, animator: Boolean = false) {
        if (animator) {
            val animator = ObjectAnimator.ofFloat(this, "progress", progress)
            animator.startDelay = 200
            animator.start()
        } else {
            this.progress = progress
        }
    }
}