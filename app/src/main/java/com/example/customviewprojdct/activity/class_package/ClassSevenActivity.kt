package com.example.customviewprojdct.activity.class_package

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.customviewprojdct.R
import com.example.customviewprojdct.activity.BaseClassActivity

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