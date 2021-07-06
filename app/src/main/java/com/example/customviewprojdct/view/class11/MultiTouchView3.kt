package com.example.customviewprojdct.view.class11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart

/**
 * 支持多手指触摸View🤌
 * 3：各自为战型
 */
private val IMAGE_SIZE = 200.dp

class MultiTouchView3 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
//    private val path = Path()
    val pathArray = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0..pathArray.size()) {
            var path = pathArray.valueAt(i)
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            //如果非最后一根手指抬起，如果不主动移除，那么坐标偏移还会按原本手势数计算，导致图片跟手指移动时"跳一下"
            MotionEvent.ACTION_DOWN -> {
//                path.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
//                path.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {

            }
            MotionEvent.ACTION_UP -> {
//                path.reset()
                invalidate()
            }
        }
        return true
    }

}