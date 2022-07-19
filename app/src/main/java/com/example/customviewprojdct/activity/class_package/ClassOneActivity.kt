package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.view.class1.DashboardView
import com.example.customviewprojdct.view.class1.PieView
import com.example.customviewprojdct.view.class1.TestView
import com.example.customviewprojdct.view.class8.SquareImageView
import com.example.customviewprojdct.view.other.DashboardProgressView
import com.example.customviewprojdct.view.other.RectProgressView

class ClassOneActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassOneActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "测试", TestView::class.java),
            Triple("2", "仪表盘", DashboardView::class.java),
            Triple("3", "饼图", PieView::class.java),
            Triple("4", "矩形进度", RectProgressView::class.java),
            Triple("5", "圆形进度", DashboardProgressView::class.java)
        )
    }

    override fun onButtonItemExtraEvent(view: View) {
        when(view){
            is RectProgressView ->{
                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
//                val params = FrameLayout.LayoutParams(200.dp.toInt(), 12.dp.toInt())
                view.layoutParams = params

                view.postDelayed({
//                    view.rectCount = 2
                    view.setProgress(80f, true)
                                 },1000)
            }
            is DashboardProgressView -> {
                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                view.layoutParams = params

                view.postDelayed({
//                    view.rectCount = 2
                    view.setProgress(80f, true)
                },1000)
            }
        }
    }

}