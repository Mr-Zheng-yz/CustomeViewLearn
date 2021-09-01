package com.example.customviewprojdct.view.other

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart


class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#666666".toColorInt()
    }

    private var translateX = 100f
    private var translateY = 100f

    override fun onDraw(canvas: Canvas) {
        canvas.translate(translateX, translateY)
//        canvas.scale(2f,2f, width / 2f, height / 2f)
        canvas.drawBitmap(getAvatart(resources,100.dp.toInt()),0f,0f,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
//                translateX = x
//                translateY = y
//                invalidate()
            }
        }
        return true
    }

}