package com.example.customviewprojdct.view.class8

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp

class OneHundredView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        background = ColorDrawable(Color.BLUE)
    }

    //第一次不听父View话（父View可修正）
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(100.dp.toInt(), 100.dp.toInt())
    }

    //第二次不听父View话（父View不可修正）
    //只是举例了解原理，实际开发中很少重写 layout()
    //作用：负责保存自己的位置和尺寸
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, (l + 100.dp).toInt(), (t + 100.dp).toInt())
    }

    //作用：摆放子View（调用每个子View的layout()方法）
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

}