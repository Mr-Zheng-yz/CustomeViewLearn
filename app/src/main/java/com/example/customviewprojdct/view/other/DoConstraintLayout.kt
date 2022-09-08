package com.example.customviewprojdct.view.other

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 *  author : baize
 *  date : 2022/8/5 3:23 下午
 *  description : 支持独立圆角
 */
class DoConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    d: Int = 0
) : ConstraintLayout(context, attrs, d) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}