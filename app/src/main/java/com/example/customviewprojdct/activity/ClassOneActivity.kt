package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.extensions.showToast
import com.example.customviewprojdct.view.class1.DashboardView
import com.example.customviewprojdct.view.class1.PieView
import com.example.customviewprojdct.view.class1.TestView
import com.example.customviewprojdct.view.class2.AvatarView
import com.example.customviewprojdct.view.class2.ScratchCardView
import com.example.customviewprojdct.view.class2.TextLoadingView
import com.example.customviewprojdct.view.class2.XfermodeViewDemoView

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
            Triple("3", "饼图", PieView::class.java)
        )
    }

}