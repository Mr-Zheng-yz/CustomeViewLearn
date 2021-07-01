package com.example.customviewprojdct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.customviewprojdct.R

class TouchEventActivity : AppCompatActivity() {
    lateinit var touchView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        touchView = findViewById<View>(R.id.touch_view)
        touchView.setOnClickListener {
            Toast.makeText(this,"哈哈",Toast.LENGTH_SHORT).show()
        }
        //外挂OnTouchListener优先于View重写的onTouchEvent方法
        touchView.setOnTouchListener { v, event ->
            Log.i("yanze","View的onTouchListener处理事件：onTouchListener")
            return@setOnTouchListener false
        }
    }
}