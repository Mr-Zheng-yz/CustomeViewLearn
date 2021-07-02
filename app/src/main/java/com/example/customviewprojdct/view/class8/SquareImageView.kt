package com.example.customviewprojdct.view.class8

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.customviewprojdct.extensions.dp
import kotlin.math.max
import kotlin.math.min

/**
 * 情景1：简单改写已有尺寸
 */
class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    //通过这种方式改写尺寸，父View并不知道(已经量好结果，不再干预)，导致父View在摆放多个View的时候会出现问题
//    override fun layout(l: Int, t: Int, r: Int, b: Int) {
//        val width = r - l
//        val height = b - t
//        val minSize = max(width,height)
//        super.layout(l, t, l + minSize, t + minSize)
//    }

    //通过这种方式改写尺寸，父View可以拿到结果并干预，不会造成布局摆放问题
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minSize = max(measuredWidth,measuredHeight)
        setMeasuredDimension(minSize,minSize)

        //自己测量的期望尺寸：
//        getMeasuredWidth()
//        getMeasuredHeight()

        //父View计算后的实际尺寸：也就是传给layout()保存的尺寸
        //测量过程中，还拿不到最终的测量结果
//        getWidth()
//        getHeight()
    }

}