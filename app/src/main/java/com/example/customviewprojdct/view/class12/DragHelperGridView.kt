package com.example.customviewprojdct.view.class12

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

//列
private const val COLUMNS = 2
//行
private const val ROWS = 3

class DragHelperGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val limitWidth = widthSize / 2
        val limitHeight = heightSize / 3
//        measureChildren(
//            MeasureSpec.makeMeasureSpec(limitWidth, widthSpec),
//            MeasureSpec.makeMeasureSpec(limitHeight, heightSpec)
//        )
        var currentWidth = 0
        var currentHeight = 0
        for ((index, child) in children.withIndex()) {
            measureChild(
                child,
                MeasureSpec.makeMeasureSpec(limitWidth, widthSpec),
                MeasureSpec.makeMeasureSpec(limitHeight, heightSpec)
            )
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            childrenBounds[index].set(
                currentWidth,
                currentHeight,
                currentWidth + limitWidth,
                currentHeight + limitHeight
            )
            //更新行高
            if (currentWidth != 0) {
                currentHeight += limitHeight
            }
            //更新行起始位置
            if (currentWidth == 0) {
                currentWidth += limitWidth
            } else {
                currentWidth = 0
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val rect = childrenBounds[index]
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }
}