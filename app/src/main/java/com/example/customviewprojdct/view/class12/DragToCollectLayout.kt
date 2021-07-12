package com.example.customviewprojdct.view.class12

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.example.customviewprojdct.R

/**
 * 通过OnDragListener实现拖拽View到收藏
 */
class DragToCollectLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var avatarView:ImageView
    lateinit var logoView:ImageView
    lateinit var linearLayoutView:LinearLayout

    /**
     * DragShadowBuilder：View被拖起来的样式，默认是半透明的被拖拽View。可以自定义View
     */
    private var dragStarter = OnLongClickListener { v ->
        val imageData = ClipData.newPlainText("name", v.contentDescription)
        ViewCompat.startDragAndDrop(v, imageData, DragShadowBuilder(v), null, 0)
        return@OnLongClickListener false
    }

    private var collectListener = CollectListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        avatarView = findViewById(R.id.avatarView)
        logoView = findViewById(R.id.logoView)
        linearLayoutView = findViewById(R.id.collectorLayout)
        //为两个图片设置拖拽
        avatarView.setOnLongClickListener(dragStarter)
        logoView.setOnLongClickListener(dragStarter)
        //为LinearLayout容器设置监听
        linearLayoutView.setOnDragListener(collectListener)
    }

    private inner class CollectListener : OnDragListener{

        override fun onDrag(v: View?, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> if (v is LinearLayout) {
                    val clipData = event.clipData.getItemAt(0).text
                    val textView = TextView(context)
                    textView.text = clipData
                    textView.setTextColor(Color.WHITE)
                    linearLayoutView.addView(textView)
                }
            }
            return true
        }

    }

}