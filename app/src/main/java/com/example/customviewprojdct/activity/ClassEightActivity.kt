package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart
import com.example.customviewprojdct.view.class6.DrawableView
import com.example.customviewprojdct.view.class8.SquareImageView

class ClassEightActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassEightActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "情景1:测量View宽高相同", SquareImageView::class.java)
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