package com.example.customviewprojdct.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.view.View
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.view.class5.*

class ClassFiveActivity : BaseClassActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClassFiveActivity::class.java))
        }
    }

    override fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf(
            Triple("1", "翻页动画", CameraAnimatorDemoView::class.java),
            Triple("2", "圆动画", CircleView::class.java),
            Triple("3", "坐标属性动画", PointView::class.java),
            Triple("4", "文字属性动画", ProvinceView::class.java)
        )
    }

    override fun onButtonItemExtraEvent(view: View) {
        when (view) {
            is CircleView -> {
                view.setOnClickListener {
                    val animator = if (view.radius == CircleView.MAX_RADIO) {
                        ObjectAnimator.ofFloat(view, "radius", CircleView.MIN_RADIO)
                    } else {
                        ObjectAnimator.ofFloat(view, "radius", CircleView.MAX_RADIO)
                    }
                    animator.startDelay = 200
                    animator.start()
                }
            }
            is PointView -> {
                //Point 坐标属性动画
                val animator = ObjectAnimator.ofObject(
                    view, "point",
                    PointFEvaluator(), PointF(0f, 0f), PointF(200.dp, 200.dp)
                )
                animator.duration = 1000
                animator.start()
            }
            is ProvinceView -> {
                //字符串属性动画
                val animator =
                    ObjectAnimator.ofObject(
                        view,
                        "province",
                        ProvinceEvaluator(),
                        "北京市",
                        "澳门特别行政区"
                    )
                animator.duration = 5000
                animator.start()
            }
        }
    }

}