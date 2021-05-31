package com.example.customviewprojdct.extensions

import android.content.res.Resources
import android.util.TypedValue

val Float.dp
    get() : Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
    }

fun Float.dp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )
}