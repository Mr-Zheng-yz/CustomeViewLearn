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
        }
    }

}