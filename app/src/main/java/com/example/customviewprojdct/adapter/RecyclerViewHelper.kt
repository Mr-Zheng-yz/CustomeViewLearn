package com.example.customviewprojdct.adapter

import android.content.Context
import com.google.android.flexbox.*

fun getFlexManager(context:Context): FlexboxLayoutManager {
    return FlexboxLayoutManager(context).apply {
        flexWrap = FlexWrap.WRAP
        flexDirection = FlexDirection.ROW
        justifyContent = JustifyContent.CENTER
        alignItems = AlignItems.CENTER
    }
}