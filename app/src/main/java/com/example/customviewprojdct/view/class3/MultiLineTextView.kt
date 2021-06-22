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
        private val RING_WIDTH = 20.dp
        private val RADIUS = 150.dp
        private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
        private val CIRCLELIGHT_COLOR = Color.parseColor("#FF4081")
    }

    //lorem ipsum
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply{
        textSize = 16.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //source：要绘制的文字
        //paint：画笔
        //width：宽度，折行
        //alignment:对齐方式（Layout.Alignment枚举）
        //spacingmult：文字间距倍数
        //spacingadd：文字间距额外值
        //includepad：是否需要行间距
        val staticLayout = StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 1f, false)
        staticLayout.draw(canvas)
    }

}