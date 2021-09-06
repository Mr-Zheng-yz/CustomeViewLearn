package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.view.class2.AvatarView
import com.example.customviewprojdct.view.class2.ScratchCardView
import com.example.customviewprojdct.view.class2.TextLoadingView
import com.example.customviewprojdct.view.class2.XfermodeViewDemoView

class ClassTwoActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassTwoActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "圆形头像", AvatarView::class.java),
            Triple("2", "刮刮卡", ScratchCardView::class.java),
            Triple("3", "文字Loading", TextLoadingView::class.java),
            Triple("4", "Xfermode效果", XfermodeViewDemoView::class.java)
        )
    }

    override fun onButtonItemExtraEvent(view: View) {
        when (view) {
            is TextLoadingView -> {
                view.startAnimator()
            }
        }
    }

}