package com.example.customviewprojdct.view.class1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.customviewprojdct.extensions.dp
import java.lang.Exception
import kotlin.math.sin

class SinLineSurfaceView @JvmOverloads constructor(
    context: Context,
    attribute: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attribute, defStyleAttr), SurfaceHolder.Callback, Runnable {

    var isDrawing = false
    var mCanvas: Canvas? = null
    var surfaceHolder: SurfaceHolder? = null

    var mX = 0f
    var mY = 0f
    var path = Path()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 2.dp
        style = Paint.Style.STROKE
    }

    init {
        surfaceHolder = getHolder()
        surfaceHolder?.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        isDrawing = true
        Thread(this).start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isDrawing = false
    }

    override fun run() {
        while (isDrawing) {
            draw()
            mX += 1
            mY = ((100 * sin(mX * 2 * Math.PI / 180)) + 400).toFloat()
            path.lineTo(mX, mY)
        }
    }

    fun draw() {
        try {
            mCanvas = surfaceHolder?.lockCanvas()
            mCanvas?.drawColor(Color.WHITE)
            mCanvas?.drawPath(path, paint)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mCanvas?.let { surfaceHolder?.unlockCanvasAndPost(it) }
        }
    }

}