package com.example.customviewprojdct.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewprojdct.R

class ClassTwoActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassTwoActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_two)
    }
}