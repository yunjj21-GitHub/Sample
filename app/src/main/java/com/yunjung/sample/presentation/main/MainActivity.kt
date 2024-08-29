package com.yunjung.sample.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.yunjung.sample.R
import com.yunjung.sample.databinding.ActivityMainBinding
import com.yunjung.sample.navigation.Navigation
import com.yunjung.sample.util.extension.setStatusBarTransparent

class MainActivity: FragmentActivity() {
    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        // StatusBar 제거
        setStatusBarTransparent(binding.container)

        // Navigation 설정
        Navigation.setNavigation(this)
    }
}