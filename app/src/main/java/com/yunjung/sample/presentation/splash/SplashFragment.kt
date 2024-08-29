package com.yunjung.sample.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentSplashBinding
import com.yunjung.sample.navigation.NavDest
import com.yunjung.sample.navigation.Navigation

class SplashFragment: BaseFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    override val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        binding.goToLoginBtn.setOnClickListener {
            Navigation.navigate(NavDest.login)
        }
    }

    override fun initObserver() {
        super.initObserver()
    }
}