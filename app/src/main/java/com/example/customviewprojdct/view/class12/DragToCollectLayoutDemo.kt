package com.example.customviewprojdct.view.class12

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.customviewprojdct.R

class DragToCollectLayoutDemo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var contentView: View = View.inflate(context, R.layout.demo_drag_to_collect_layout, this)

}