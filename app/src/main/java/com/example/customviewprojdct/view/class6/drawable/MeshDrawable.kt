package com.example.customviewprojdct.view.class6.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColorInt
import com.example.customviewprojdct.extensions.dp

private val MESH_PADDING = 40.dp
class MeshDrawable : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#F9AB25".toColorInt()
        strokeWidth = 5.dp
    }

    override fun draw(canvas: Canvas) {
        for (i in bounds.left..bounds.right step MESH_PADDING.toInt()) {
            canvas.drawLine(i.toFloat(),bounds.top.toFloat()
                ,i.toFloat(),bounds.bottom.toFloat(),paint)
        }
        for (i in bounds.top..bounds.bottom step MESH_PADDING.toInt()) {
            canvas.drawLine(bounds.left.toFloat(),i.toFloat()
                ,bounds.right.toFloat(),i.toFloat(),paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT  //全透明
            0xFF -> PixelFormat.OPAQUE  //不透明
            else -> PixelFormat.TRANSLUCENT //半透明
        }
    }

}