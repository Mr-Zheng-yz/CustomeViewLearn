package com.example.customviewprojdct.view.class4

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val IMAGE_PADDING = 100.dp
private val IMAGE_SIZE = 200.dp

class TestCanvasChangeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val cilpedPath = Path().apply {
        addOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_SIZE,
            IMAGE_PADDING + IMAGE_SIZE,
            Path.Direction.CW
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //1.裁切成矩形：
//        canvas.clipRect(
//            IMAGE_PADDING,
//            IMAGE_PADDING,
//            IMAGE_PADDING + IMAGE_SIZE / 2,
//            IMAGE_PADDING + IMAGE_SIZE / 2
//        )
        //2.裁切成圆形
//        canvas.clipPath(cilpedPath)

        //正向思考图形（错误）：
//        canvas.translate(IMAGE_PADDING,0f)
//        canvas.rotate(45f, 2*IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
//        canvas.drawBitmap(getAvatart(IMAGE_SIZE.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)

        //正向思考坐标（难度）：
//        canvas.translate(IMAGE_PADDING,0f)
//        canvas.rotate(45f, IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
//        canvas.drawBitmap(getAvatart(IMAGE_SIZE.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)

        //反向思考图形（推荐）
        canvas.rotate(45f, 2*IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.translate(IMAGE_PADDING,0f)
        canvas.drawBitmap(getAvatart(IMAGE_SIZE.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)

        //画Canvas坐标辅助线
        val linesX = floatArrayOf(0f,0f,width.toFloat(),0f
            ,0f,IMAGE_PADDING,width.toFloat(),IMAGE_PADDING
            ,0f,2*IMAGE_PADDING,width.toFloat(),2*IMAGE_PADDING
            ,0f,3*IMAGE_PADDING,width.toFloat(),3*IMAGE_PADDING
            ,0f,4*IMAGE_PADDING,width.toFloat(),4*IMAGE_PADDING
            ,0f,5*IMAGE_PADDING,width.toFloat(),5*IMAGE_PADDING
        )
        val linesY = floatArrayOf(0f,0f,0f,height.toFloat()
            ,IMAGE_PADDING,0f,IMAGE_PADDING,height.toFloat()
            ,2*IMAGE_PADDING,0f,2*IMAGE_PADDING,height.toFloat()
            ,3*IMAGE_PADDING,0f,3*IMAGE_PADDING,height.toFloat()
            ,4*IMAGE_PADDING,0f,4*IMAGE_PADDING,height.toFloat()
            ,5*IMAGE_PADDING,0f,5*IMAGE_PADDING,height.toFloat()
        )
        canvas.drawLines(linesX,paint)
        canvas.drawLines(linesY,paint)
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