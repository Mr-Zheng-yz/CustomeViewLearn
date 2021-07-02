package com.example.customviewprojdct.view.other

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.graphics.toColorInt
import com.example.customviewprojdct.extensions.dp

private val EXTRA_VERTICAL_OFFSET = 16.dp

class JKLikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#666666".toColorInt()
        textSize = 16.dp
        textAlign = Paint.Align.CENTER
    }
    var number : Int = 88
    var nextNumber = 0
    var fraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    var liked = false
        set(value){
            if (field != value) {
                field = value
                if (field) {
                    nextNumber = number + 1
                    hideAnimator.start()
                } else {
                    nextNumber = number - 1
                    number -= 1
                    hideAnimator.reverse()
                }
            }
        }

    private val hideAnimator by lazy {
        ObjectAnimator.ofFloat(this,"fraction",0f,1f)
    }

    private val showAnimator by lazy {
        ObjectAnimator.ofFloat(this,"fraction",1f,0f)
    }

    init {
        hideAnimator.doOnEnd {
            number = nextNumber
            Log.i("yanze","animator end!")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //无需改动文字
//        canvas.drawText(nextNumber.toString(), width / 2f, height / 2f - EXTRA_VERTICAL_OFFSET, paint)

        //新文字
        paint.alpha = (0xff * fraction).toInt()
        val offset = EXTRA_VERTICAL_OFFSET * fraction
        canvas.drawText(nextNumber.toString(), width / 2f, height / 2f - EXTRA_VERTICAL_OFFSET + offset, paint)

        //旧文字
        paint.alpha = (0xff * (1 - fraction)).toInt()
        canvas.drawText(number.toString(), width / 2f, height / 2f + offset, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                liked = !liked
            }
        }
        return true
    }

}