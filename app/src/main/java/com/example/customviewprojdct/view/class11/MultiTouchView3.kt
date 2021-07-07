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
import androidx.core.util.forEach
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart

/**
 * 支持多手指触摸View🤌
 * 3：各自为战型
 */
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
    val pathArray = SparseArray<Path>()
    val drawedArray = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until pathArray.size()) {
            val path = pathArray.valueAt(i)
            canvas.drawPath(path, paint)
        }
        for (i in 0 until drawedArray.size()) {
            val path = drawedArray.valueAt(i)
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN -> {
                val newPath = Path()
                newPath.moveTo(event.getX(event.actionIndex),event.getY(event.actionIndex))
                pathArray.append(event.getPointerId(event.actionIndex), newPath)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                //几根手指非常快速的落下，滑动，抬起也会报错...
                for (i in 0 until pathArray.size()) {
//                  //这里默认 遍历的index 和 手指数下标是重合的
//                    val actionId = event.getPointe标是重合的
                    val actionId = event.getPointerId(i)
                    val path = pathArray.get(actionId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
                //这样拿会报错：pointerIndex out of range
//                pathArray.forEach { key, value ->
//                    val index = event.findPointerIndex(key)
//                    value.lineTo(event.getX(index),event.getY(index))
//                }
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP,MotionEvent.ACTION_UP -> {
                val pointerUpId = event.getPointerId(event.actionIndex)
                drawedArray.append(System.currentTimeMillis().toInt(),pathArray.get(pointerUpId))
                pathArray.remove(pointerUpId)
                invalidate()
            }
        }
        return true
    }

}