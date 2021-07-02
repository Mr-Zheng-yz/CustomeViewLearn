package com.example.customviewprojdct.view.class9

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class TouchViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    //在一次时间序列的过程中，调用之后禁止父View对该事件序列拦截
    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 为onTouchEvent中事件处理做好准备工作，因为onTouchEvent只能收到最新事件。
        // 如：坐标记录
        Log.i("yanze","ViewGroup是否拦截事件：onInterceptTouchEvent()")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //如果事件直接发生到ViewGroup身上，也要做好准备工作
        Log.i("yanze","ViewGroup处理事件：onTouchEvent()")
        return super.onTouchEvent(event)
    }

}