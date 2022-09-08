package com.example.customviewprojdct.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewprojdct.R

class TestActivity : AppCompatActivity() {

    lateinit var testView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testView = findViewById(R.id.test_view)

        testView.setOnClickListener {
            startActivity(Intent(this@TestActivity, MainActivity::class.java))
        }
    }
}