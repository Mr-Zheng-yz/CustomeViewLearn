package com.example.customviewprojdct.view.class2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customviewprojdct.extensions.dp


class XfermodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * 是否和官方文档一致：
     *  官方文档在做混合时，算上了src和dst图像的整个透明背景一起做计算，而广大网友一般用drawOval和drawRect方法只画出了方和圆
     */
    private var isOfficial: Boolean = true
    private var PORTERDUFF_XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bound = RectF(150f.dp, 50f.dp, 300f.dp, 200f.dp)
    private val circleBitmap = Bitmap.createBitmap(150.dp.toInt(),150.dp.toInt(),Bitmap.Config.ARGB_8888)
    private val squareBitmap = Bitmap.createBitmap(150.dp.toInt(),150.dp.toInt(),Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81D60")
        canvas.drawOval(50f.dp, 0.dp, 150.dp, 100.dp, paint)
        canvas.setBitmap(squareBitmap)
        paint.color = Color.parseColor("#2196F3")
        canvas.drawRect(0.dp, 50.dp, 100.dp, 150.dp, paint)
    }

    override fun onDraw(canvas: Canvas) {
        if (isOfficial) {
            val count = canvas.saveLayer(bound, paint)
            canvas.drawBitmap(circleBitmap,150.dp,50.dp,paint)
            paint.xfermode = PORTERDUFF_XFERMODE
            canvas.drawBitmap(squareBitmap,150.dp,50.dp,paint)
            paint.xfermode = null
            canvas.restoreToCount(count)
        } else {
            val count = canvas.saveLayer(bound, paint)
            paint.color = Color.parseColor("#D81D60")
            canvas.drawOval(200f.dp, 50f.dp, 300f.dp, 150f.dp, paint)
            paint.xfermode = PORTERDUFF_XFERMODE
            paint.color = Color.parseColor("#2196F3")
            canvas.drawRect(150.dp, 100f.dp, 250.dp, 200f.dp, paint)
            paint.xfermode = null
            canvas.restoreToCount(count)
        }

    }

    fun setOfficial(boolean: Boolean) {
        isOfficial = boolean
        invalidate()
    }

    fun setXfermode(mode: PorterDuff.Mode?) {
        if (mode == null) return
        PORTERDUFF_XFERMODE = PorterDuffXfermode(mode)
        invalidate()
    }

}