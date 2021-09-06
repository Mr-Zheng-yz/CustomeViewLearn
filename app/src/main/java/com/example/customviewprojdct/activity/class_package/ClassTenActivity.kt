package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.view.class10.ScalableImageView

class ClassTenActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassTenActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "ScalableImageView", ScalableImageView::class.java)
        )
    }

}