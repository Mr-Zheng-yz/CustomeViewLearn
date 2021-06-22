package com.example.customviewprojdct.view.class2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp


class ScratchCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var PORTERDUFF_XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    private var bound = RectF()
    private var imageHeight = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10.dp
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        color = Color.RED
    }
    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.scratch_card,options)
        imageHeight = (width.toFloat() / options.outWidth) * options.outHeight
        Log.i("yanze","imageHeight: $imageHeight")
        bound = RectF(0f,0f,width.toFloat(),imageHeight)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(getAvatart(width),0f,0f,paint)
        paint.reset()
        val count = canvas.saveLayer(bound,paint)
        paint.color = Color.LTGRAY
        canvas.drawRect(bound,paint)
        paint.reset()
        paint.xfermode = PORTERDUFF_XFERMODE
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 40.dp
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            color = Color.RED
        }
        canvas.drawPath(path,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x,event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x,event.y)
                invalidate()
            }
        }
        return true
    }

    /**
     * 获取指定大小Bitmap，读两次
     * 1.只读Bounds大小，不读原图，读取很快
     * 2.计算原图和目标比例，按需要读原图
     */
    fun getAvatart(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.scratch_card,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.scratch_card,options)
    }
}