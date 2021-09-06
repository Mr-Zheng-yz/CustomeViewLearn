package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.view.class3.MultiLineTextView
import com.example.customviewprojdct.view.class3.SportDemoView

class ClassThreeActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassThreeActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "文字测量", SportDemoView::class.java),
            Triple("2", "多行文字", MultiLineTextView::class.java)
        )
    }

}