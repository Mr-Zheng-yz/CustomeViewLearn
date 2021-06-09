package com.example.customviewprojdct.view.class2

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toRectF
import com.example.customviewprojdct.extensions.dp

class TextLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var RECT_BORDER = RectF()
    private val TEXT_HEIGHT_OFFEST = 6.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2.dp
        textSize = 66.dp
        color = Color.GREEN
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }
    var isAnimator = true
    private var text = "Baize"
    private var progress = 0f

    fun setProgress(value: Float) {
        this.progress = value
    }
    fun getProgress(): Float {
        return progress
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val textBorder = Rect()
        paint.getTextBounds(text, 0, text.length, textBorder)
        val textWidth = paint.measureText(text)
        RECT_BORDER.left = (width / 2) - (textWidth / 2)
        RECT_BORDER.top = ((height / 2 - textBorder.height()).toFloat())
        RECT_BORDER.right = (width / 2) + (textWidth / 2)
        RECT_BORDER.bottom = (height / 2).toFloat() + TEXT_HEIGHT_OFFEST
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val count = canvas.saveLayer(RECT_BORDER, paint)
        canvas.drawText(text, (width / 2).toFloat(), (height / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#FF0000")
        canvas.drawRect(
            RECT_BORDER.left,
            RECT_BORDER.top + (RECT_BORDER.height() * (1 - progress)),
            RECT_BORDER.right,
            RECT_BORDER.bottom,
            paint
        )
        paint.xfermode = null
        canvas.restoreToCount(count)

//        progress += 0.01f
//        if (progress > 1) {
//            progress = 0f
//        }
//        Log.i("yanze","progress:${progress}")
//        if (isAnimator) {
//            postInvalidateDelayed(500)
//        }
    }

    fun startAnimator() {
//        isAnimator = true
//        invalidate()
        Log.i("yanze", "startAnimator:")
        val animator = ObjectAnimator.ofFloat(this, "progress", 0f, 1f)
        animator.duration = 1000
        animator.start()
    }

    fun stopAnimator() {
        isAnimator = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimator()
    }

}