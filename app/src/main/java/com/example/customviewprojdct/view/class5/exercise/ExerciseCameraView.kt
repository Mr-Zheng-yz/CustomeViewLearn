package com.example.customviewprojdct.view.class5.exercise

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val IMAGE_PADDING = 100.dp
private val IMAGE_SIZE = 200.dp

class ExerciseCameraView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val bitmap = getAvatart(IMAGE_SIZE.toInt())
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val camera = Camera()

    var topFlip = 0f
        set(value){
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value){
            field = value
            invalidate()
        }
    var flipRotate = 0f
        set(value){
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        //上半部分
        canvas.withSave {
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            canvas.rotate(-flipRotate)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0f)
            canvas.rotate(flipRotate)
            canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE / 2), -(IMAGE_PADDING + IMAGE_SIZE / 2))
            canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        }
        //下半部分
        canvas.withSave {
            canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
            canvas.rotate(-flipRotate)
            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE)
            canvas.rotate(flipRotate)
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