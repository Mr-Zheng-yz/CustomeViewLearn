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

/**
 * DragViewHelper实现
 */
class DragHelperGridViewDemo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var contentView: View = View.inflate(context, R.layout.demo_drag_helper_grid_layout, this)
//    var dragView: DragHelperGridView

    init {
        //demo_drag_layout
        //demo_drag_to_collect_layout
        //        dragView = contentView.findViewById(R.id.drag_layout)
    }

}