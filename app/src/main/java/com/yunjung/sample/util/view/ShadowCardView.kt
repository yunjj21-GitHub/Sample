package com.yunjung.sample.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yunjung.sample.databinding.LayoutShadowCvBinding

class ShadowCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutShadowCvBinding.inflate(LayoutInflater.from(context), this, true)
}