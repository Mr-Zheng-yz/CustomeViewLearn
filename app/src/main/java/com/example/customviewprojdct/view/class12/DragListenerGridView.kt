package com.example.customviewprojdct.view.class12

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.DragStartHelper
import androidx.core.view.ViewCompat
import androidx.core.view.children

//列
private const val COLUMNS = 2

//行
private const val ROWS = 3

/**
 * 使用 OnDragListener 实现拖拽
 */
class DragListenerGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private var onDragListener = HenDragListener()
    private lateinit var draggedView : View
    private var orderChildren = mutableListOf<View>()

    init {
        isChildrenDrawingOrderEnabled = true
    }

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
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in children.withIndex()) {
            //先全都摆放到左上角，然后通过偏移完成摆放，这样所有子 View 坐标统一，方便做动画
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            orderChildren.add(child)
            child.setOnLongClickListener { v ->
                /**
                 * data：可以「跨进程」，而且要包装成ClipData类型，并且只有松手的时候才能拿到（DragEvent.ACTION_DROP时）因为跨进程数据比较重
                 * myLocalState：本地数据，随时都能拿到，通过 event.localState
                 */
                draggedView = v
                //兼容实现
                ViewCompat.startDragAndDrop(v, ClipData.newPlainText("name",v.contentDescription), DragShadowBuilder(v),v,0)
                v.startDrag(null, DragShadowBuilder(v), v, 0)
                false
            }
            child.setOnDragListener(onDragListener)
        }
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        return super.onDragEvent(event)
    }

    private inner class HenDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.i("yanze", "拖拽刚刚开始：startDrag()被调用的时候")
                    if (event.localState === v) {  //只隐藏被长按的 View
                        v.visibility = View.INVISIBLE
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    if (event.localState !== v) {  //如果其他View被拖拽的View入侵，则触发重排
                        sort(v)
                    }
                    Log.i("yanze", "拖拽到了某个View的区域内都会触发（包括自己）")
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.i("yanze", "离开到了某个View的区域（包括自己）")
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (event.localState === v) {
                        v.visibility = View.VISIBLE
                    }
                }
                DragEvent.ACTION_DROP -> {
                    Log.i("yanze","手指抬起🤌")
                }
            }
            return true
        }
    }

    /**
     * 找到拖拽的View和被入侵View，重新计算位置，通过动画移动
     */
    private fun sort(targetView: View) {
        var draggedIndex = -1
        var targetIndex = -1
        for ((index, child) in orderChildren.withIndex()) {
            if (child === targetView) {
                targetIndex = index
            } else if (child === draggedView) {
                draggedIndex = index
            }
        }
        orderChildren.removeAt(draggedIndex)
        orderChildren.add(targetIndex, draggedView)
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in orderChildren.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .setDuration(160)
        }
    }
}















