package com.example.customviewprojdct.view.class8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.extensions.dp

//情景二：完全自定义View尺寸
class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var RADUIS = 100.dp
    val PADDING = 50.dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(RADUIS + PADDING,RADUIS + PADDING,RADUIS,paint)
    }

    //让圆的半径来决定View大小
    //父View意见（开发者和可用空间要求）：widthMeasureSpec heightMeasureSpec
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //这里需要参考父类传入的开发者限制尺寸，配合自己的期望尺寸来计算
        val size = ((RADUIS + PADDING) * 2).toInt()
        //不考虑父 View 建议直接测
//        setMeasuredDimension(size,size)

        //自己写测量规则
//        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
//        val widthValue = MeasureSpec.getSize(widthMeasureSpec)
//        var width = when (widthSpec) {
//            //精确尺寸,直接按父 View 要求来
//            MeasureSpec.EXACTLY -> widthValue
//            //最大限制模式
//            MeasureSpec.AT_MOST -> if (size > widthValue) widthValue else size
//            //不限制 MeasureSpec.UNSPECIFIED
//            else -> size
//        }

        //使用Android提供API测量
        val width = resolveSize(size,widthMeasureSpec)
        val height = resolveSize(size,heightMeasureSpec)
        setMeasuredDimension(width,height)
    }

}