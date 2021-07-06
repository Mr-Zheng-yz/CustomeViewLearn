package com.example.customviewprojdct.view.class10

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.customviewprojdct.extensions.dp
import com.example.customviewprojdct.extensions.getAvatart
import kotlin.math.log
import kotlin.math.max
import kotlin.math.min

private val IMAGE_SIZE = 300.dp
private val EXTRA_SCALE = 1.5f

class ScalableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    private val bitmap = getAvatart(resources, 300.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f

    //滑动任务
    private val flingRunnable = HenFlingRunnable()
    //手势监听
    private val gestureDetectorListener = HenGestureDetector()
    private val gestureDetector = GestureDetectorCompat(context, gestureDetectorListener)
    //捏撑监听🤏
    private val scaleGestureDetectorListener = HenScaleGestureDetectorListener()
    private val scaleGestureDetector = ScaleGestureDetector(context,scaleGestureDetectorListener)
    private var big = false
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val scaleAnimator  = ObjectAnimator.ofFloat(this, "currentScale",0f,1f)
//        ObjectAnimator.ofFloat(this, "currentScale", 0f, 1f).apply {
//            doOnEnd {
                //加上跟随手指双击放大功能后，这里可以去掉了
                //修复由于offsetX和offsetY没有重置导致图片再次放大时产生额外偏移：动画缩小后，重置图片偏移
//            if (!big) {
//                offsetX = 0f
//                offsetY = 0f
//            }
//            }
//        }
//    }
    private val overScroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w - IMAGE_SIZE) / 2
        originalOffsetY = (h - IMAGE_SIZE) / 2
        //如果图片的宽高比，比View的宽高比大，说明图片比较胖，小图缩放比 = 图片宽：View宽，大图缩放比 = 图片高 ： View高
        //如果图片宽高比 < View宽高比，说明图片图片在View里比较瘦，小图缩放比 = 图片高 ： View高，大图缩放比 = 图片宽：View宽
        if (bitmap.width / bitmap.height.toFloat() > w / h.toFloat()) {
            smallScale = w.toFloat() / bitmap.width
            bigScale = h.toFloat() / bitmap.height * EXTRA_SCALE
        } else {
            smallScale = h.toFloat() / bitmap.height
            bigScale = w.toFloat() / bitmap.width * EXTRA_SCALE
        }
        //初始化
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale,bigScale)
        Log.i("yanze", "big:$bigScale small:$smallScale")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //计算动画完成度，决定了偏移影响度
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        //让图片完成度对图片偏移产生影响
        canvas.translate(offsetX * scaleFraction,offsetY * scaleFraction)
//        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    //两种手势同时监听，当捏撑时，由捏撑监听起抢夺事件
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        //进入捏撑流程，不再处理双击等手势
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }


    /**
     * 手势操作
     */
    inner class HenGestureDetector : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
//            Log.i("yanze","$distanceX $distanceY")
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
//            Log.i("yanze","offset:$offsetX $offsetY")
                invalidate()
            }
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                //1.计算，原点选择图片正中心位置,起始点就直接是offsetX和offsetY了
                overScroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt()
                    , (-(bitmap.width * bigScale - width) / 2).toInt()
                    , ((bitmap.width * bigScale - width) / 2).toInt()
                    , (-(bitmap.height * bigScale - height) / 2).toInt()
                    , ((bitmap.height * bigScale - height) / 2).toInt()
                )
                //生硬的调用
                //        for (i in 10..1000 step 10) {
                //            postDelayed({ refresh() }, i.toLong())
                //        }
                ViewCompat.postOnAnimation(this@ScalableImageView,flingRunnable)
            }
            return false
        }

        //双击
        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                //双击放大，增加手指偏移（放大手指双击位置）
                //求出初始偏移？？
                offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
                Log.i("yanze","${offsetX} ${offsetY} : ${(1 - bigScale / smallScale)}")
                //限制边界
                fixOffsets()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }

        fun fixOffsets() {
            offsetX = min(offsetX,(bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX,-(bitmap.width * bigScale - width) / 2)
            offsetY = min(offsetY,(bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY,-(bitmap.height * bigScale - height) / 2)
        }

    }

    /**
     * 滑动计算刷新位置
     */
    inner class HenFlingRunnable : Runnable{
        //2.掐表拿值
        override fun run() {
            //计算当前时间对应值
            if (overScroller.computeScrollOffset()) {
                //获取值
                offsetX = overScroller.currX.toFloat()
                offsetY = overScroller.currY.toFloat()
                invalidate()
                //用lambda表达式做任务，每次都会创建对象，影响性能：postOnAnimation { refresh() }
                //postOnAnimation(this@ScalableImageView)：高版本API才有该方法，为了兼容行，使用下面这种方式
                ViewCompat.postOnAnimation(this@ScalableImageView,this)
            }
        }
    }

    /**
     * 捏撑手势🤏
     */
    inner class HenScaleGestureDetectorListener : ScaleGestureDetector.OnScaleGestureListener {
        //捏撑过程，返回值：返回你是否消费了当前放缩系数
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // detector.scaleFactor：【返回放缩系数】：
            // 如果返回false，拿到的就是当前状态和初始状态的比值；
            // 如果返回true，返回的就是当前状态和上一状态的比值。
            // 如果进行修正，那么不消费本次放缩系数（一直保留）
            val tempCurrentScale = currentScale * detector.scaleFactor
            return if (tempCurrentScale < smallScale || tempCurrentScale > bigScale) {
                false
            } else {
                currentScale = tempCurrentScale
                true
            }
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            //放缩增加捏撑中心点的初始偏移（修复捏撑不跟手）
            offsetX = (detector.focusX - width / 2f) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2f) * (1 - bigScale / smallScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }
    }

}


