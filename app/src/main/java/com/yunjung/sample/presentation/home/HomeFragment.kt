package com.yunjung.sample.presentation.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
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
        binding.adVp.currentItem = getInitialRealPos()
        updateAdIndicator(0)
        playAd(true)
        binding.adVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(realPos: Int) {
                updateAdIndicator(shownPos(realPos))
            }
        })
        binding.leftBtn.setOnClickListener { moveAdView(DIR.LEFT) }
        binding.rightBtn.setOnClickListener { moveAdView(DIR.RIGHT) }
        binding.playSwitch.setOnCheckedChangeListener { _, isPlay -> playAd(isPlay) }
    }

    enum class DIR { LEFT, RIGHT }
    private fun moveAdView(dir: DIR){
        val realPos = binding.adVp.currentItem
        val nextRealPos = when(dir) {
            DIR.LEFT -> {
                if(realPos - 1 > 0) realPos - 1
                else getInitialRealPos()
            }
            DIR.RIGHT -> {
                if(realPos + 1 < MAX_VP_ITEM_NUM) realPos + 1
                else getInitialRealPos()
            }
        }
        binding.adVp.currentItem = nextRealPos
        updateAdIndicator(shownPos(nextRealPos))
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
        val adListSize = viewModel.getAdListSize()
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

    private val MAX_VP_ITEM_NUM = 1000
    private inner class AdVpAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int = MAX_VP_ITEM_NUM

        override fun createFragment(realPos: Int): Fragment {
            val ad = viewModel.adList.value!![shownPos(realPos)]
            return AdPageFragment.newInstance(ad)
        }
    }

    private fun shownPos(realPos: Int): Int = realPos % viewModel.getAdListSize()

    private fun getInitialRealPos(): Int = ((MAX_VP_ITEM_NUM / 2) / viewModel.getAdListSize()) * (viewModel.getAdListSize())
}