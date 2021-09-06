package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewprojdct.R
import com.example.customviewprojdct.activity.class_package.NestedScrollImageActivity

/**
 * 同方向滑动冲突场景
 */
class ScrollClashActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ScrollClashActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clash)
    }
}