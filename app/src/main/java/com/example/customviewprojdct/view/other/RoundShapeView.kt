package com.example.customviewprojdct.view.other

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart


class RoundShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#666666".toColorInt()
    }
    private var paintC = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#00ff00".toColorInt()
    }

    private val rect = Rect()
    private val path = Path()
    private var leftTopRadius = 10.dp
    private var leftBottomRadius = 20.dp

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0, 0, w, h)
    }

//    val count = canvas.saveLayer(bound, bgPaint)
//    canvas.drawLine(0F, 0F, width.toFloat(), 0F, bgPaint)
//    progressPaint.xfermode = xfermodeSrcIn
//    val progressWidth = (progress / maxProgress) * width
//    canvas.drawRect(0F, 0F, progressWidth, height.toFloat(), progressPaint)
//    progressPaint.xfermode = null
//    canvas.restoreToCount(count)

    override fun onDraw(canvas: Canvas) {

        canvas.drawRect(rect, paint)
        canvas.drawCircle(leftTopRadius, leftTopRadius, leftTopRadius, paintC)
        canvas.drawCircle(leftBottomRadius, height - leftBottomRadius, leftBottomRadius, paintC)
    }

}