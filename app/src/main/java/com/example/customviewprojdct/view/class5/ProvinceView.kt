package com.example.customviewprojdct.view.class5

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customviewprojdct.extensions.dp

private val provinces = arrayOf("北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省"
    ,"山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区")

class ProvinceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 50.dp
        textAlign = Paint.Align.CENTER
    }

    var province = "北京市"
        set(value){
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(province, width / 2f, height / 2f, paint)
    }

}

class ProvinceEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startIndex = provinces.indexOf(startValue)
        val endIndex = provinces.indexOf(endValue)
        val currentIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
        Log.i("yanze","index：${currentIndex}")
        return provinces[currentIndex]
    }

}