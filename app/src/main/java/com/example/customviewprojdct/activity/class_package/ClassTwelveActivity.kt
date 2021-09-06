package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.customviewprojdct.activity.BaseClassActivity
import com.example.customviewprojdct.view.class12.DragHelperGridViewDemo
import com.example.customviewprojdct.view.class12.DragListenerGridViewDemo
import com.example.customviewprojdct.view.class12.DragToCollectLayoutDemo
import com.example.customviewprojdct.view.class12.DragUpDownLayoutDemo

class ClassTwelveActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassTwelveActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "(DragListener)网格拖拽", DragListenerGridViewDemo::class.java),
            Triple("1", "(DragHelper)网格拖拽", DragHelperGridViewDemo::class.java),
            Triple("2", "(DragListener)拖拽到收藏", DragToCollectLayoutDemo::class.java),
            Triple("1", "(DragHelper)上or下拖拽", DragUpDownLayoutDemo::class.java)
        )
    }

}