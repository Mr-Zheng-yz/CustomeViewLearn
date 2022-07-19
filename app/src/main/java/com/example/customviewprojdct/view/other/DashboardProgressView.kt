package com.example.customviewprojdct.view.other

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart
import com.example.customviewprojdct.view.class3.SportView
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.min

/**
 * 分数仪表板，带文字显示
 */
class DashboardProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0
) : View(context, attrs, d) {

    private var progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 6.dp
    }
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        style = Paint.Style.FILL
    }

    private var currentRadius = 0f

    var progress = 0F
        set(value) {
            field = value
            invalidate()
        }

    var progressColor: Int // = "#12D9AA".toColorInt()

    var progressTextColor: Int // = "#000000".toColorInt()

    var circleBackgroundColor: Int // = "#FFFFFF".toColorInt()

    var title: String? = null //"score"
    var titleTextColor: Int // = "#BABABA".toColorInt()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardProgressView)
        progressColor = typeArray.getColor(R.styleable.DashboardProgressView_progressColor, "#12D9AA".toColorInt())
        progressTextColor = typeArray.getColor(R.styleable.DashboardProgressView_progressTextColor, "#000000".toColorInt())
        circleBackgroundColor = typeArray.getColor(R.styleable.DashboardProgressView_circleBackgroundColor, "#FFFFFF".toColorInt())
        titleTextColor = typeArray.getColor(R.styleable.DashboardProgressView_titleTextColor, "#BABABA".toColorInt())
        title = typeArray.getString(R.styleable.DashboardProgressView_titleText)
        typeArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentRadius = (min(w, h) / 2) - progressPaint.strokeWidth / 2
    }

    private val bounds = Rect()

    override fun onDraw(canvas: Canvas) {
        textPaint.color = circleBackgroundColor
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), currentRadius, textPaint)
        progressPaint.color = progressColor
        canvas.drawArc(
            width / 2 - currentRadius,
            height / 2 - currentRadius,
            width / 2 + currentRadius,
            height / 2 + currentRadius,
            0f,
            progress / 100 * 360f,
            false,
            progressPaint
        )
        textPaint.isFakeBoldText = true
        textPaint.textSize = 24.dp
        textPaint.color = progressTextColor

        val progressText: String = String.format("%.1f", progress)
        textPaint.getTextBounds(progressText, 0, progressText.length, bounds)
        val offsetY: Float = (bounds.top + bounds.bottom) / 2f
        canvas.drawText(
            progressText,
            width / 2f,
            if (title?.isNotBlank() == true) height / 2f - offsetY - 6.dp else height / 2f - offsetY,
            textPaint
        )
        if (title?.isNotBlank() == true) {
            textPaint.isFakeBoldText = false
            textPaint.textSize = 11.dp
            textPaint.color = titleTextColor
            textPaint.getTextBounds(title, 0, title!!.length, bounds)
            var titleOffsetY: Float = (bounds.top + bounds.bottom) / 2f
            canvas.drawText(
                title,
                width / 2f,
                height / 2f - (titleOffsetY * 2) - offsetY,
                textPaint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = 100.dp.toInt()
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    fun setProgress(progress: Float, animator: Boolean = false) {
        if (animator) {
            val animator = ObjectAnimator.ofFloat(this, "progress", progress)
            animator.interpolator = DecelerateInterpolator()
            animator.duration = 600
            animator.start()
        } else {
            this.progress = progress
        }
    }

}