package com.yunjung.sample.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentSplashBinding
import com.yunjung.sample.navigation.NavDest
import com.yunjung.sample.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment: BaseFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    override val viewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            Navigation.navigate(NavDest.login)
        }
    }

    override fun initView() {
    }

    override fun initObserver() {
        super.initObserver()
    }
}