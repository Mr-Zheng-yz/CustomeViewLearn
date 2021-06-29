package com.example.customviewprojdct.activity

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.view.class5.PointFEvaluator
import com.example.customviewprojdct.view.class5.PointView
import com.example.customviewprojdct.view.class5.ProvinceEvaluator
import com.example.customviewprojdct.view.class5.ProvinceView

class TestActivity : AppCompatActivity() {

    lateinit var testView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testView = findViewById(R.id.test_view)

        testView.setOnClickListener {
            //Point属性动画
//            val animator = ObjectAnimator.ofObject(testView,"point",
//                PointFEvaluator(),PointF(0f,0f),PointF(200.dp,200.dp))
//            animator.duration = 1000
//            animator.start()

            //字符串属性动画
//            val animator = ObjectAnimator.ofObject(testView,"province",ProvinceEvaluator(),"北京市","澳门特别行政区")
//            animator.duration = 5000
//            animator.start()
        }
    }
}