package com.yunjung.sample.util.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("layout_marginTop")
    fun setLayoutMarginTop(view: View, marginTop: Int){
        val marginParams = view.layoutParams as ViewGroup.MarginLayoutParams
        marginParams.topMargin = marginTop
        view.requestLayout()
    }
}