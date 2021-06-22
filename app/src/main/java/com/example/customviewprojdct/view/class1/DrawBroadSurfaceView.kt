package com.example.customviewprojdct.view.class1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.customviewprojdct.extensions.dp
import java.lang.Exception
import kotlin.math.sin

class DrawBroadSurfaceView @JvmOverloads constructor(
    context: Context,
    attribute: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attribute, defStyleAttr), SurfaceHolder.Callback, Runnable {

    var isDrawing = false
    var mCanvas: Canvas? = null

    var path = Path()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 2.dp
        style = Paint.Style.STROKE
    }

    init {
        holder?.addCallback(this)
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
        val start = System.currentTimeMillis()
        while (isDrawing) {
            draw()
        }
        val end = System.currentTimeMillis()
        if (end - start < 100) {
            Thread.sleep(100 - (end - start))
        }
    }

    private fun draw() {
        try {
            mCanvas = holder?.lockCanvas()
            mCanvas?.drawColor(Color.WHITE)
            mCanvas?.drawPath(path, paint)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mCanvas?.let { holder?.unlockCanvasAndPost(it) }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
        }
        return true
    }

}