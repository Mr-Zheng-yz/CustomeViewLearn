package com.example.customviewprojdct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customviewprojdct.R
import com.example.customviewprojdct.activity.class_package.*
import com.example.customviewprojdct.extensions.showToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun classEntrance(v: View) {
        when (v.id) {
            R.id.class_1 -> {
                ClassOneActivity.start(this)
            }
            R.id.class_2 -> {
                ClassTwoActivity.start(this)
            }
            R.id.class_3 -> {
                ClassThreeActivity.start(this)
            }
            R.id.class_4 -> {
                ClassFourActivity.start(this)
            }
            R.id.class_5 -> {
                ClassFiveActivity.start(this)
            }
            R.id.class_6 -> {
                ClassSixActivity.start(this)
            }
            R.id.class_7 -> {
                ClassSevenActivity.start(this)
            }
            R.id.class_8 -> {
                ClassEightActivity.start(this)
            }
            R.id.class_9 -> {
                showToast("了解View和ViewGroup事件分发时序源码逻辑～")
            }
            R.id.class_10 -> {
                ClassTenActivity.start(this)
            }
            R.id.class_11 -> {
                ClassElevenActivity.start(this)
            }
            R.id.class_12 -> {
                ClassTwelveActivity.start(this)
            }
            R.id.class_13 -> {
                NestedScrollImageActivity.start(this)
            }
            R.id.class_14 -> {
                ScrollClashActivity.start(this)
            }
        }
    }

}