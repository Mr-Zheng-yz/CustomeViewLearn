package com.example.customviewprojdct.view.class5

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val IMAGE_PADDING = 100.dp
private val IMAGE_SIZE = 200.dp

class CameraAnimatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //属性1：上半部分三维旋转角度
    var topFlip = 0f
        set(value){
            field = value
            invalidate()
        }

    //属性2：下半部分三维旋转角度
    var bottomFlip = 0f
        set(value){
            field = value
            invalidate()
        }

    //属性3：canvas旋转角度
    var flipRotation = 0f
        set(value){
            field = value
            invalidate()
        }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = getAvatart(IMAGE_SIZE.toInt())
    val camera = Camera().apply {
        setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        //上半部分
        canvas.withSave {
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0f)
            canvas.rotate(flipRotation)
            canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
            canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
            canvas.restore()
        }

        //下半部分
        canvas.withSave {
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE)
            canvas.rotate(flipRotation)
            canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
            canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        }
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