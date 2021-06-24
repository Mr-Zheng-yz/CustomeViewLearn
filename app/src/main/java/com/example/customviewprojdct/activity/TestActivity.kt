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

        testView.setOnClickListener {
            testView.animate()
                .rotation(40f)
                .alpha(50f)
                .scaleX(2f)
                .scaleY(2f)
                .translationX(300f)
                .translationY(800f)
                .duration = 1000
        }
    }
}