package com.example.customviewprojdct.view.class3

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 复杂多行文字
 */
class MultiLineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private val IMAGE_SIZE = 150.dp
        private val IMAGE_PADDING = 50.dp
    }

    //lorem ipsum
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vel augue neque. Proin gravida iaculis lorem. Phasellus ac pretium sapien. Mauris faucibus hendrerit lacus, a consectetur dolor pulvinar vel. Nullam sagittis mauris nec lobortis iaculis. Suspendisse sit amet purus at lectus interdum lacinia. Cras ac venenatis orci. Nam consectetur scelerisque luctus. Nunc pulvinar venenatis neque quis dignissim. Quisque metus sem, egestas eget magna quis, elementum accumsan est. Sed vel condimentum sem. Morbi hendrerit, augue eget interdum eleifend, turpis ex feugiat turpis, sit amet aliquam nibh justo sed diam. Sed vel magna velit. Mauris in nisi nec dui faucibus porttitor ac a nisi."
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatart(IMAGE_SIZE.toInt())
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //1. 使用StaticLayout绘制普通多行文字
        //source：要绘制的文字
        //paint：画笔
        //width：宽度，折行
        //alignment:对齐方式（Layout.Alignment枚举）
        //spacingmult：文字间距倍数
        //spacingadd：文字间距额外值
        //includepad：是否需要行间距
//        val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 1f, false)
//        staticLayout.draw(canvas)

        //画图
        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)

        //绘制文字
        paint.textSize = 16.dp
        paint.getFontMetrics(fontMetrics)
        val measuredWidth = floatArrayOf(0f)
        //绘制多行文本
        var start = 0
        var count: Int
        var verticalOffsetY = -fontMetrics.top
        var maxWidth : Float
        while (start < text.length) {
            //辅助基线
            canvas.drawLine(12.dp, verticalOffsetY, width - 12.dp, verticalOffsetY, paint)
            //图片范围
            maxWidth = if (verticalOffsetY + fontMetrics.bottom > IMAGE_PADDING
                && verticalOffsetY + fontMetrics.top < IMAGE_PADDING + IMAGE_SIZE) {
                width - 150.dp
            } else width.toFloat()
            //反向思考
//          maxWidth = if (verticalOffsetY + fontMetrics.bottom < IMAGE_PADDING
//                || verticalOffsetY + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
//                width.toFloat()
//            } else width - IMAGE_SIZE
            count = paint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffsetY, paint)
            start += count
            verticalOffsetY += paint.fontSpacing
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