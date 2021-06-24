package com.example.customviewprojdct.view.class4

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val IMAGE_PADDING = 100.dp
private val IMAGE_SIZE = 200.dp

class CameraView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = getAvatart(IMAGE_SIZE.toInt())
    val camera = Camera().apply {
        rotateX(30f)
        setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        //上半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-30f)
        canvas.clipRect(-IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0f)
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE)
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
    }

    fun getAvatart(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }
}