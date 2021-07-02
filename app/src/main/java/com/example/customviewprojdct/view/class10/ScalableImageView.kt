package com.example.customviewprojdct.view.class10

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart

private val IMAGE_SIZE = 300.dp

class ScalableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bitmap = getAvatart(resources, 300.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f
    private val smallScale = 0f
    private val bigScale = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (w - IMAGE_SIZE) / 2
        offsetY = (h - IMAGE_SIZE) / 2
        //如果图片的宽高比，比View的宽高比大，说明图片比较胖，小图缩放比 = 图片宽：View宽，大图缩放比 = 图片高 ： View高
        //如果图片宽高比 < View宽高比，说明图片图片在View里比较瘦，小图缩放比 = 图片高 ： View高，大图缩放比 = 图片宽：View宽
        if()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.scale(1.5f, 1.5f)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

}