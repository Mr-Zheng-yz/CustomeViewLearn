package com.example.customviewprojdct.view.class2

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ToggleButton
import com.example.customviewprojdct.R

class XfermodeViewDemoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var xfermodeview: XfermodeView
    private var radio_group: RadioGroup
    private var toggle_button: ToggleButton
    private val xfermodes = arrayListOf<Pair<String, PorterDuff.Mode>>(
        //Alpha compositing modes 透明度合成模式
        Pair("SRC", PorterDuff.Mode.SRC),   //源像素替换目标像素 The source pixels replace the destination pixels.
        Pair("SRC_OVER", PorterDuff.Mode.SRC_OVER),  //源像素绘制在目标像素上。 The source pixels are drawn over the destination pixels.
        Pair("SRC_IN", PorterDuff.Mode.SRC_IN),//保持覆盖了目标像素的源像素，丢弃剩余的源和目标像素 Keeps the source pixels that cover the destination pixels, discards the remaining source and destination pixels.
        Pair("SRC_ATOP", PorterDuff.Mode.SRC_ATOP),// 丢弃不覆盖目标像素的源像素。在目标像素上绘制剩余的源像素。 Discards the sourcegi pixels that do not cover destination pixels. Draws remaining source pixels over destination pixels.
        Pair("DST", PorterDuff.Mode.DST),//丢弃源像素，目标像素保持不变 The source pixels are discarded, leaving the destination intact.
        Pair("DST_OVER", PorterDuff.Mode.DST_OVER),//源像素绘制在目标像素后面。 The source pixels are drawn behind the destination pixels.
        Pair("DST_IN", PorterDuff.Mode.DST_IN),//保留覆盖源像素的目标像素，丢弃剩余的源和目标像素。 Keeps the destination pixels that cover source pixels, discards the remaining source and destination pixels.
        Pair("DST_ATOP", PorterDuff.Mode.DST_ATOP),//丢弃源像素未覆盖的目标像素。在源像素上绘制剩余的目标像素。 Discards the destination pixels that are not covered by source pixels. Draws remaining destination pixels over source pixels.
        Pair("CLEAR", PorterDuff.Mode.CLEAR),//源像素覆盖的目标像素被清除为0（相交部分为透明）。 Destination pixels covered by the source are cleared to 0.
        Pair("SRC_OUT", PorterDuff.Mode.SRC_OUT),//保留不覆盖目标像素的源像素。丢弃覆盖目标像素的源像素。丢弃所有目标像素。 Keeps the source pixels that do not cover destination pixels. Discards source pixels that cover destination pixels. Discards all destination pixels.
        Pair("DST_OUT", PorterDuff.Mode.DST_OUT),//保留未被源像素覆盖的目标像素。丢弃被源像素覆盖的目标像素。丢弃所有源像素。 Keeps the destination pixels that are not covered by source pixels. Discards destination pixels that are covered by source pixels. Discards all source pixels.
        Pair("Exclusive Or", PorterDuff.Mode.XOR),//丢弃源像素和目标像素覆盖部分，绘制剩余部分。 Discards the source and destination pixels where source pixels cover destination pixels. Draws remaining source pixels.
        //Blending modes    混合模式
        Pair("DARKEN", PorterDuff.Mode.DARKEN),//保留源像素和目标像素的最小分量。 Retains the smallest component of the source and destination pixels.
        Pair("LIGHTEN", PorterDuff.Mode.LIGHTEN),//保留源像素和目标像素的最大分量。 Retains the largest component of the source and destination pixel.
        Pair("MULTIPLY", PorterDuff.Mode.MULTIPLY),//将源像素和目标像素相乘。 Multiplies the source and destination pixels.
        Pair("SCREEN", PorterDuff.Mode.SCREEN),//将源像素和目标像素相加，然后减去源像素乘以目标像素。 Adds the source and destination pixels, then subtracts the source pixels multiplied by the destination.
        Pair("OVERLAY", PorterDuff.Mode.OVERLAY) //根据目标颜色相乘或屏蔽源和目标。Multiplies or screens the source and destination depending on the destination color.
    )
    private val radioButtons = ArrayList<RadioButton>(xfermodes.size)

    init {
        val contentView: View = View.inflate(context, R.layout.view_xfermode_demo, this)
        xfermodeview = contentView.findViewById(R.id.xfermodeview)
        radio_group = contentView.findViewById(R.id.radio_group_src)
        toggle_button = contentView.findViewById(R.id.toggle_button)

        xfermodes.forEach {
            val button = generateButton(it)
            radio_group.addView(button)
            radioButtons.add(button)
        }
        radioButtons[0].isChecked = true
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            val targetView = radioButtons.find { it.id == checkedId }
            xfermodeview.setXfermode(targetView?.tag as? PorterDuff.Mode)
        }
        toggle_button.setOnCheckedChangeListener { buttonView, isChecked ->
            xfermodeview.setOfficial(isChecked)
        }
    }

    private fun generateButton(params:Pair<String, PorterDuff.Mode>): RadioButton {
        val radioButton = RadioButton(context)
        radioButton.text = params.first
        radioButton.tag = params.second
        return radioButton
    }

}