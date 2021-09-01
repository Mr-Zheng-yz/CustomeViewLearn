package com.example.customviewprojdct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewprojdct.R

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
        }
    }

}