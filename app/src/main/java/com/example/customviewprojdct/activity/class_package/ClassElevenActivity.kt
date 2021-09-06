package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.view.class11.*

class ClassElevenActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassElevenActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "1.接力型", MultiTouchView1::class.java),
            Triple("2", "2.配合型", MultiTouchView2::class.java),
            Triple("3", "3.各自为战型", MultiTouchView3::class.java),
            Triple("4", "两页ViewPage", TwoViewPager::class.java),
            Triple("5", "三页ViewPage", ThreeViewPager::class.java)
        )
    }

    override fun onButtonItemExtraEvent(view: View) {
        when (view) {
            is TwoViewPager -> {
                view.removeAllViews()
                val view1 = View(this)
                view1.background = ColorDrawable(Color.RED)
                view1.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                val view2 = View(this)
                view2.background = ColorDrawable(Color.GREEN)
                view2.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                view.addView(view1)
                view.addView(view2)
            }
            is ThreeViewPager -> {
                view.removeAllViews()
                val view1 = View(this)
                view1.background = ColorDrawable(Color.RED)
                view1.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                val view2 = View(this)
                view2.background = ColorDrawable(Color.GREEN)
                view2.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                val view3 = View(this)
                view3.background = ColorDrawable(Color.YELLOW)
                view3.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                view.addView(view1)
                view.addView(view2)
                view.addView(view3)
            }
        }
    }

}