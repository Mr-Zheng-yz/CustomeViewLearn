package com.example.customviewprojdct.view.class8

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val childrenBounds = mutableListOf<Rect>()

    //难点：计算子 View 位置
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //1. 计算出对子 View 的限制：widthMeasureSpec 和 heightMeasureSpec
        //  1）开发者配置（父 View 对我的限制，开发者对子 View 的限制），从layoutParams获取    2）可用空间： 父View限制；除去已使用部分；
        //2. 保存子 View 测量结果
        //3. 根据所有子 View 测量结果确定自己的宽高
        var lineWidthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        var lineMaxWidth = 0
        val parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        for ((index, child) in children.withIndex()) {
            //测量子View，用0去测得到子View真实宽度
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            //换行时机：测到的子View宽度大于一行剩下的宽度，则换行
            if (parentWidthMode != MeasureSpec.UNSPECIFIED && child.measuredWidth > parentWidthSize - lineWidthUsed) {
                lineWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                //重新测量（只重新测量一次，由于布局特性，测量多次结果一样）
                measureChildWithMargins(child, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed)
            }

            //存储测量结果
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            childrenBounds[index].set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight)

            lineWidthUsed += child.measuredWidth
            lineMaxWidth = max(lineMaxWidth,lineWidthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }

        //确定自己宽高
        val selfWidth = resolveSizeAndState(lineMaxWidth, widthMeasureSpec, 0)
        val selfHeight = resolveSizeAndState(heightUsed + lineMaxHeight, heightMeasureSpec, 0)
        setMeasuredDimension(selfWidth, selfHeight)
    }

    //简单：将位置传给子 View
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // l,t,t,b：该 ViewGroup 相对于父布局位置
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(lp: LayoutParams?): MarginLayoutParams {
        return MarginLayoutParams(lp)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

}