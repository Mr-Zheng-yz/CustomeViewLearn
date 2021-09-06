package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.view.class8.CircleView
import com.example.customviewprojdct.view.class8.OneHundredView
import com.example.customviewprojdct.view.class8.SquareImageView
import com.example.customviewprojdct.view.class8.TagLayoutDemo

class ClassEightActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassEightActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "情景1:测量View宽高相同", SquareImageView::class.java),
            Triple("2", "情景2：完全自定义View尺寸", CircleView::class.java),
            Triple("3", "无论怎样尺寸都为100的View", OneHundredView::class.java),
            Triple("4", "情景3:Layout的自定义", TagLayoutDemo::class.java)
        )
    }

    override fun onButtonItemExtraEvent(view: View) {
        when(view){
            is SquareImageView ->{
                val params = FrameLayout.LayoutParams(200.dp.toInt(),60.dp.toInt())
                view.layoutParams = params
                view.setBackgroundColor(Color.YELLOW)
            }
        }
    }

}