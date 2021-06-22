package com.example.customviewprojdct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customviewprojdct.R

class TestActivity : AppCompatActivity() {

    lateinit var testView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testView = findViewById(R.id.test_view)
    }
}