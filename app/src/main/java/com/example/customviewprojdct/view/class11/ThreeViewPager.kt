package com.example.customviewprojdct.view.class11

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class ThreeViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    //惯性滑动的时候，计算出初始速度
    private var velocityTracker = VelocityTracker.obtain()
    //惯性滑动计算器
    private var overScroller = OverScroller(context)
    //获取系统配置的View相关参数
    private var viewConfiguration = ViewConfiguration.get(context)
    //最小快滑速度
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    //最大快滑速度(限制不小心快滑)
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    //滑动阙值
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop
    //滑动状态，方便其他地方判断是否再滑动
    private var scrolling = false
    //记录按下位置
    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    //当前页面位置
    private var currentPage = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //按我的宽高来测量所有子View
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = -width
        var childRight = 0
        //直接按我的宽度来摆放
        for (child in children) {
            child.layout(childLeft, 0, childRight, height)
            childLeft += width
            childRight = childLeft + width
        }
    }

    override fun shouldDelayChildPressedState() = true

    //拦截算法：看滑动长度是否达到阙值，达到了则认为是滑动
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        //拦截前准备，与 onTouchEvent 的 DOWN 事件一致
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            //抢夺算法：
            MotionEvent.ACTION_MOVE -> {
                //滑动过程中，不再判断抢夺
                if (!scrolling) {
                    val dx = downX - event.x
                    //如果滑动距离大于滑动阙值，则抢夺事件，同时希望父View不要抢夺我的事件
                    if (abs(dx) > pagingSlop) {
                        scrolling = true
                        requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //计算当前速度：
        // units：单位，多少毫秒移动多少像素  0...如，1000，每秒移动多少像素
        // maxVelocity:回调速度上限
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                //这个 scrollX 记录的是已经滑动了多少距离
                downScrollX = scrollX.toFloat()
                Log.i("yanze","已经滑动了：${scrollX}")
            }
            MotionEvent.ACTION_MOVE -> {
                //注意 scrollTo 方法滑动的值是倒着计算的，同时增加范围限制
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtMost(width)
                    .coerceAtLeast(-width)
                Log.i("yanze", "已滑动：${scrollX} 位置：${dx}")
                scrollTo(dx, 0)
            }
            //停靠算法：
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000)
                val vx = velocityTracker.xVelocity
                //如果是快速滑动，那么判断滑动方向，向同方向的子View停靠；否则，根据滑动位置判断停靠
                val diff = if (abs(vx) < minVelocity) { //不是快滑
                    if (abs(scrollX) > width / 2) {
                        //如果scrollX滑动距离是负的，那么说明ViewGroup在负方向滑动
                        if (scrollX < 0) -1 else 1
                    } else 0
                } else { //快滑，vx 大于0是负方向滑动；小于0是正方向滑动。
                    Log.i("yanze", "快速滑动！")
                    if (vx < 0) 1 else -1
                }
                Log.i("yanze", "差：${diff}")
                val targetPage = (currentPage + diff)
                if (targetPage >= -1 && targetPage <= 1) {
                    val scrollX = -(event.x - downX).toInt()
                    val scrollDistanceX = when {
                        (currentPage == targetPage) -> -scrollX
                        //负向滑动，已滑动距离是负值
                        (currentPage > targetPage) -> -(width - abs(scrollX))
                        //负向滑动，已滑动距离是正值
                        (currentPage < targetPage) -> (width - scrollX)
                        else -> 0
                    }
                    Log.i("yanze", "本次滑动距离为：${scrollX} ${getScrollX()} 距离目标页距离：${scrollDistanceX} 目标页：${targetPage} 当前页:$currentPage 滑动速度为：${vx} 最大速度：${maxVelocity} 最小速度：$minVelocity")
                    currentPage = targetPage
                    //计算滑动到终点所需时间
                    overScroller.startScroll(getScrollX(), 0, scrollDistanceX, 0)
                    postInvalidateOnAnimation()
                }
            }
        }
        return true

    }

    //postInvalidateOnAnimation()会触发重绘（draw()）
    //该方法会在 draw() 方法中被调用
    //达到在滑动计算器（OverScroller🧮）未完成到达终点之前，反复绘制当前位置的目的
    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            //拿到每一时刻的滑动位置
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }

}