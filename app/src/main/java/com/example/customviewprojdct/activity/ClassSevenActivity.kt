package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.showToast
import com.example.customviewprojdct.view.class2.AvatarView
import com.example.customviewprojdct.view.class2.ScratchCardView
import com.example.customviewprojdct.view.class2.TextLoadingView
import com.example.customviewprojdct.view.class2.XfermodeViewDemoView
import com.example.customviewprojdct.view.class3.MultiLineTextView
import com.example.customviewprojdct.view.class3.SportDemoView
import com.example.customviewprojdct.view.class4.CameraView
import com.example.customviewprojdct.view.class4.TestCanvasChangeView
import com.example.customviewprojdct.view.class6.DrawableView
import com.example.customviewprojdct.view.class7.MaterialEditText

class ClassSevenActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassSevenActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_seven)
    }

    override fun useDefaultOnCreate() = false

}