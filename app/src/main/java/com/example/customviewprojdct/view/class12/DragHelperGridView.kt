package com.example.customviewprojdct.view.class12

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

//列
private const val COLUMNS = 2
//行
private const val ROWS = 3

/**
 * 使用 DragStartHelper 实现拖拽
 */
class DragHelperGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    //1. 创建ViewDragHelper对象
    private var dragHelper = ViewDragHelper.create(this,DragHelperCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft : Int
        var childTop : Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in children.withIndex()) {
            //巧妙的算法
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft,childTop,childLeft + childWidth, childTop + childHeight)
        }
    }

    //2. 代理ViewGroup触摸方法
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    //2. 代理ViewGroup触摸方法
    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        //该方法中会自动处理偏移，所以只需要根据View移动是否完成决定是否重绘下一帧
        if (dragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

    //拖拽回调
    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        var capturedLeft = 0f
        var capturedTop = 0f

        //唯一必须事件：表示是否尝试将这个View拖动，返回 true ：View跟手走；返回 false：View不跟手
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        //钳制View
        //默认返回0，表示限制View偏移（拖不动）
        //返回left和top，表示不限制偏移
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        //View开始拖拽的时候调用（可以做一些初始化操作）
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation = elevation + 1     //盖住其他View
            //记录初始位置，放回去的时候会用到
            capturedLeft = capturedChild.left.toFloat()
            capturedTop = capturedChild.top.toFloat()
        }

        //当View做坐标发生改变的时候调用（可以在其中实现一些拖拽逻辑，如重拍）
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }

        //停止拖拽的时候被调用（放开View👋）
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //将拖拽的View放到指定位置并开始计算，类似OverScroller的使用，这里将拖拽View放回原来位置
            dragHelper.settleCapturedViewAt(capturedLeft.toInt(), capturedTop.toInt())
            postInvalidateOnAnimation() // 强制重绘
        }
    }

}