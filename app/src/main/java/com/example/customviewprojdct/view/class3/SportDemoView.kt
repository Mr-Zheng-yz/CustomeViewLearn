package com.example.customviewprojdct.view.class3

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.example.customviewprojdct.R

class SportDemoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var sportView: SportView

    val testTexts = mutableListOf<String>("abab","aaaa","bbbb","abgl","gggg","llll","国家力量","中国移动","0002","9999")
    var index = 0

    init {
        val contentView: View = View.inflate(context, R.layout.sport_demo_view, this)
        sportView = contentView.findViewById<SportView>(R.id.sport_view)
        contentView.findViewById<Button>(R.id.btn_text_change).setOnClickListener {
            sportView.setDrawText(testTexts[index])
            index = (index + 1) % testTexts.size
        }
        contentView.findViewById<ToggleButton>(R.id.tb_text_mode).setOnCheckedChangeListener { buttonView, isChecked ->
            sportView.setDrawTextModel(if (isChecked) SportView.DrawTextModel.DYNAMIC_MODE else SportView.DrawTextModel.STATIC_MODE)
        }
        contentView.findViewById<ToggleButton>(R.id.tb_text_font).setOnCheckedChangeListener { buttonView, isChecked ->
            sportView.setIsCustomerTextFont(isChecked)
        }
    }

}