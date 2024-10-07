package com.yunjung.sample.presentation.home

import android.graphics.drawable.GradientDrawable
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentAdPageBinding
import com.yunjung.sample.domain.model.Advertisement


class AdPageFragment: BaseFragment<FragmentAdPageBinding>(
    R.layout.fragment_ad_page
) {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var ad: Advertisement

    companion object {
        fun newInstance(ad: Advertisement) = AdPageFragment().apply {
            this.ad = ad
        }
    }

    override fun initView() {
        val leftColor = requireContext().getColor(ad.leftColorResId)
        val rightColor = requireContext().getColor(ad.rightColorResId)
        binding.layout.background = GradientDrawable().apply {
            orientation = GradientDrawable.Orientation.LEFT_RIGHT
            colors = intArrayOf(leftColor, rightColor)
            cornerRadius = 0.45f
        }
        binding.img.setImageResource(ad.imgResId)
        binding.summaryTxt.setText(ad.summaryResId)
        binding.contentTxt.setText(ad.contentResId)
    }
}