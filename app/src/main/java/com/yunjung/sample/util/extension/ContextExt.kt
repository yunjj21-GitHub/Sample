package com.yunjung.sample.util.extension

import android.annotation.SuppressLint
import android.content.Context

private var _statusBarHeight: Int? = null
@SuppressLint("DiscouragedApi")
fun Context.getStatusBarHeight(): Int {
    if(_statusBarHeight != null) {
        return _statusBarHeight!!
    }else{
        val resourceId = resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )

        val statusBarHeight =
            if(resourceId > 0) resources.getDimensionPixelSize(resourceId)
            else 0

        _statusBarHeight = statusBarHeight
        return statusBarHeight
    }
}