package com.example.customviewprojdct.view.class2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val IMAGE_WIDTH: Float = 200.dp
private val IMAGE_PADDING: Float = 20.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //离屏缓冲大小（离屏缓冲有性能损耗，所以挖出区域越小越好）
    private val bounds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        //开启离屏缓冲
        var count = canvas.saveLayer(bounds,null)
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH,
            paint
        )
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatart(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        //恢复离屏缓冲
        canvas.restoreToCount(count)
    }

    /**
     * 获取指定大小Bitmap，读两次
     * 1.只读Bounds大小，不读原图，读取很快
     * 2.计算原图和目标比例，按需要读原图
     */
    fun getAvatart(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar,options)
    }
}