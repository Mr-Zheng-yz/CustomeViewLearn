package com.example.customviewprojdct.view.class8

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.customviewprojdct.R

private val provinces = arrayOf("北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省"
    ,"山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区")

class TagLayoutDemo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var contentView: View
    var tagView: TagLayout

    init {
        contentView = View.inflate(context, R.layout.demo_tag_layout, this)
        tagView = contentView.findViewById(R.id.tag_layout)
        contentView.findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            refresh()
        }
        refresh()
    }

    private fun refresh() {
        tagView.removeAllViews()
        for (province in provinces) {
            val textView = ColoredTextView(context)
            textView.text = province
            tagView.addView(textView)
        }
    }

}