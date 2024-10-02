package com.yunjung.sample.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentHomeBinding
import com.yunjung.sample.util.SmplLogger
import com.yunjung.sample.util.view.SmplAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()

    override fun initView() { }

    override fun initObserver() {
        super.initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LoadingView 테스트
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.showAppLoading()
            delay(1500)
            viewModel.hideAppLoading()
        }

        activity.smplAppBar.show(binding.scrollView, binding.layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.smplAppBar.hide(binding.layout)
    }
}