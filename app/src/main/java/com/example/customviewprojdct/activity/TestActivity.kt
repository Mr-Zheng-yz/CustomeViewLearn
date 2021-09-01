package com.example.customviewprojdct.activity

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.view.class5.PointFEvaluator
import com.example.customviewprojdct.view.class5.PointView
import com.example.customviewprojdct.view.class5.ProvinceEvaluator
import com.example.customviewprojdct.view.class5.ProvinceView
import com.example.customviewprojdct.view.class8.ColoredTextView
import com.example.customviewprojdct.view.class8.TagLayout

class TestActivity : AppCompatActivity() {

    lateinit var testView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testView = findViewById(R.id.test_view)

        testView.setOnClickListener {
        }
    }
}