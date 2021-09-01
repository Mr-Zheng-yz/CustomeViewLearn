package com.example.customviewprojdct.view.class5

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.*
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

class CameraAnimatorDemoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var cameraView: CameraAnimatorView

    init {
        //1. ViewPropertyAnimator做简单属性动画
//        testView.setOnClickListener {
//            testView.animate()
//                .rotation(40f)
//                .alpha(50f)
//                .scaleX(2f)
//                .scaleY(2f)
//                .translationX(300f)
//                .translationY(800f)
//                .duration = 1000
//        }

        //2. ObjectAnimator做自定义属性动画
//        val animator = ObjectAnimator.ofFloat(testView,"radius",200.dp)
//        animator.startDelay = 1000
//        animator.start()

        val contentView: View = View.inflate(context, R.layout.demo_camera_animator_view, this)
        cameraView = contentView.findViewById(R.id.camera_view)

        cameraView.setOnClickListener {
            //3. AnimatorSet 设置多个属性动画的执行顺序
            val bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip",  30f)
            bottomFlipAnimator.duration = 1000

            val flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation",  360f)
            flipRotationAnimator.duration = 2000

            val topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip",  -30f)
            topFlipAnimator.duration = 1000
            //使用AnimatorSet管理
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator)
            animatorSet.start()

            //4.使用propertyValueHolder做动画(多个属性合成到同一个动画中)
//            val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 0f,30f)
//            val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 0f,360f)
//            val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", 0f,-30f)
//            val animator = ObjectAnimator.ofPropertyValuesHolder(cameraView,bottomFlipHolder,flipRotationHolder,topFlipHolder)
//            animator.duration = 1000
//            animator.start()

            //5.keyframe，关键帧，精确控制某一属性
//            val length = 200.dp
            //参数：fraction:百分比  value:对应属性值
//            val keyframe1 = Keyframe.ofF+loat(0f,0f)
//            val keyframe2 = Keyframe.ofFloat(0.2f,0.4f * length)
//            val keyframe3 = Keyframe.ofFloat(0.8f,1.5f * length)
//            val keyframe4 = Keyframe.ofFloat(1f,length)
//            val propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationY",keyframe1,keyframe2,keyframe3,keyframe4)
//            val animator = ObjectAnimator.ofPropertyValuesHolder(it,propertyValuesHolder)
//            animator.duration = 1000
//            animator.start()

            //6. interpolator
//            val animator = ObjectAnimator.ofFloat(it,"translationY",0f,200.dp)
//            animator.interpolator = DecelerateInterpolator()
//            animator.start()

            //7. TypeEvaluator 精确计算出动画每一刻（动画完成度）的属性值
//            TypeEvaluator()
        }
    }

}