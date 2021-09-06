package com.example.customviewprojdct.view.class12

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

/**
 * ViewDragHelper
 */
class DragUpDownLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val dragCallback = DragCallback()
    private val dragHelper = ViewDragHelper.create(this,dragCallback)
    private val viewConfiguration = ViewConfiguration.get(context)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        //放开纵向滑动
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        //releasedChild：被拖拽的View
        //xvel / yvel：横向/纵向滑动速度
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            Log.i("yanze","xvel:$xvel yvel:${yvel}")
            if (abs(yvel) > viewConfiguration.scaledMinimumFlingVelocity) { //判断快滑
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            } else {
                //这里对比的是剩余空间，另一种思维方式...
                if (releasedChild.top > height - releasedChild.bottom) {  //普通滑动，滑到下半部分
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else{
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            }
            postInvalidateOnAnimation()
        }

    }

}