package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.extensions.showToast
import com.example.customviewprojdct.view.class2.AvatarView
import com.example.customviewprojdct.view.class2.ScratchCardView
import com.example.customviewprojdct.view.class2.TextLoadingView
import com.example.customviewprojdct.view.class2.XfermodeViewDemoView
import com.example.customviewprojdct.view.class3.MultiLineTextView
import com.example.customviewprojdct.view.class3.SportDemoView
import com.example.customviewprojdct.view.class4.CameraView
import com.example.customviewprojdct.view.class4.TestCanvasChangeView

class ClassFourActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassFourActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "几何变换思想", TestCanvasChangeView::class.java),
            Triple("2", "例子", CameraView::class.java)
        )
    }

}