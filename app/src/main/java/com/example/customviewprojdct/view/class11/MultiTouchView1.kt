package com.example.customviewprojdct.view.class11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart

/**
 * 支持多手指触摸View🤌
 * 1：接力型
 */
private val IMAGE_SIZE = 200.dp

class MultiTouchView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bitmap = getAvatart(resources, IMAGE_SIZE.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var downPoint = PointF()
    //当前触摸的手指
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //第一根手指按下，记录第一根手指
                trackingPointerId = event.getPointerId(0)
                downPoint.x = event.x
                downPoint.y = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                //第二根手指按下，之后MOVE由第二根手指接管，并更新位置
                trackingPointerId = event.getPointerId(event.actionIndex)
                downPoint.x = event.getX(event.actionIndex)
                downPoint.y = event.getY(event.actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                //event.getX()拿到的是 index 为0的坐标，也就是第一根手指
                val index = event.findPointerIndex(trackingPointerId)
                Log.i("yanze", "move index：${index} id:$trackingPointerId")
                offsetX = event.getX(index) - downPoint.x + originalOffsetX
                offsetY = event.getY(index) - downPoint.y + originalOffsetY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                //非最后一根手指抬起
                //如果抬起的是正在跟踪的手指，那么找到另一个可用手指；否则忽略
                if (trackingPointerId == event.getPointerId(event.actionIndex)) {
                    //找到新的接力手指：最后一根可用手指，记得要排除抬起的手指
                    val newIndex = if (event.actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    //更新手指Id和图片位置
                    trackingPointerId = event.getPointerId(newIndex)
                    downPoint.x = event.getX(newIndex)
                    downPoint.y = event.getY(newIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }
            MotionEvent.ACTION_UP -> {
                //最后一根手指抬起
            }
        }
        return true
    }

}