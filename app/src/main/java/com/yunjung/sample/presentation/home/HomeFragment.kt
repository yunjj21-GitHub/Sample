package com.yunjung.sample.presentation.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var adViewPlayJob: Job

    override fun initView() {
        // LoadingView 테스트
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.showAppLoading()
            delay(1500)
            viewModel.hideAppLoading()
        }

        activity.smplAppBar.show(binding.scrollView, binding.layout)

        // LatestCompanyNewsView
        binding.latestCompanyNewsTxt.isSelected = true
        binding.latestCompanyNewsCloseBtn.setOnClickListener {
            binding.latestCompanyNewsContainer.visibility = View.GONE
        }

        // AdView
        viewModel.updateAdList()
        binding.adVp.adapter = AdVpAdapter(activity)
        updateAdIndicator(0)
        playAd(true)
        binding.adVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) {
                updateAdIndicator(pos)
            }
        })
        binding.leftBtn.setOnClickListener { moveAdView(DIR.LEFT) }
        binding.rightBtn.setOnClickListener { moveAdView(DIR.RIGHT) }
        binding.playSwitch.setOnCheckedChangeListener { _, isPlay -> playAd(isPlay) }
    }

    enum class DIR { LEFT, RIGHT }
    private fun moveAdView(dir: DIR){
        val adListSize = viewModel.adList.value!!.size
        val pos = binding.adVp.currentItem
        val nextPos = when(dir) {
            DIR.LEFT -> {
                if(pos - 1 > 0) pos - 1
                else adListSize - 1
            }
            DIR.RIGHT -> {
                if(pos + 1 < adListSize) pos + 1
                else 0
            }
        }
        binding.adVp.currentItem = nextPos
        updateAdIndicator(nextPos)
    }

    private fun playAd(isPlay: Boolean){
        if(isPlay) {
            adViewPlayJob = CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    delay(5000)
                    moveAdView(DIR.RIGHT)
                }
            }
        }else {
            adViewPlayJob.cancel()
        }
    }

    private fun updateAdIndicator(pos: Int){
        val adListSize = viewModel.adList.value!!.size
        var result = ""
        if(pos < 10) result += "0${pos + 1} / "
        else result += "${pos + 1} / "
        if(adListSize < 10) result += "0$adListSize"
        else result += adListSize
        binding.adIndicatorTxt.text = result
    }

    override fun initObserver() {
        super.initObserver()
        activity.smplAppBar.appBarHeight.observe(this){
            binding.appBarHeight = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.smplAppBar.hide(binding.layout)
    }

    private inner class AdVpAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int = viewModel.adList.value!!.size

        override fun createFragment(pos: Int): Fragment {
            return AdPageFragment.newInstance(viewModel.adList.value!![pos])
        }
    }
}