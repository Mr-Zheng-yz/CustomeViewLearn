package com.example.customviewprojdct.view.class2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC)

class XfermodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bound = RectF(150f.dp,50f.dp,300f.dp,200f.dp)

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bound, paint)
        paint.color = Color.parseColor("#D81D60")
        canvas.drawOval(200f.dp, 50f.dp, 300f.dp, 150f.dp, paint)

        paint.xfermode = XFERMODE

        paint.color = Color.parseColor("#2196F3")
        canvas.drawRect(150.dp, 100f.dp, 250.dp, 200f.dp, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

}