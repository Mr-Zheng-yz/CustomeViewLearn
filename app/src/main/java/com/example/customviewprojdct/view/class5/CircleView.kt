package com.example.customviewprojdct.view.class5

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object{
        val MAX_RADIO = 200.dp
        val MIN_RADIO = 50.dp
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    //Kotlin为每个属性强制加set和get方法：
    var radius = 50.dp
        set(value) {
            field = value
            invalidate()
        }

    //java的set方法：
//    fun setRadius(radius: Float) {
//        this.radius = radius
//        invalidate()
//    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(width / 2f,height / 2f,radius,paint)
    }

}