package com.example.customviewprojdct.view.class7

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.customviewprojdct.BuildConfig
import com.example.customviewprojdct.R
import com.example.customviewprojdct.extensions.dp

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 22.dp
private val EXTRA_VERTICAL_OFFSET = 25.dp

/**
 * @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
 使用这种方式居然无法获取焦点！！
 */
class MaterialEditText constructor(
    context: Context,
    attrs: AttributeSet
) : AppCompatEditText(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false
    private val floatingLabenAnimator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    //floatingLabel 开关
    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom
                    )
                } else {
                    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
                }
            }
        }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel =
            typeArray.getBoolean(R.styleable.MaterialEditText_userFloatingLabel, true)
        typeArray.recycle()

        //知晓原理，放肆改
//        val typeArray = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.useMaterialThemeColors))
//        useFloatingLabel = typeArray.getBoolean(0,true)

//        for (i in 0 until attrs.attributeCount) {
//            println("attrs: ${attrs.getAttributeName(i)} value:${attrs.getAttributeValue(i)}")
//        }

        paint.textSize = TEXT_SIZE
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (useFloatingLabel) {
            if (text.isNullOrEmpty() && floatingLabelShown) {
                //隐藏
                floatingLabelShown = false
                floatingLabenAnimator.reverse()
            } else if (!text.isNullOrEmpty() && !floatingLabelShown) {
                floatingLabelShown = true
                floatingLabenAnimator.start()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (useFloatingLabel) {
            paint.alpha = (0xff * floatingLabelFraction).toInt()
            val currentVerticalOffset =
                VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
            canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVerticalOffset, paint)
        }
    }

}