package com.example.customviewprojdct.extensions

import android.content.res.Resources
import android.util.TypedValue
import java.lang.reflect.TypeVariable

val Int.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)

fun Int.dp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat()
        , Resources.getSystem().displayMetrics
    )
}