package com.yunjung.sample.presentation.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.yunjung.sample.R
import com.yunjung.sample.databinding.ActivityMainBinding
import com.yunjung.sample.navigation.Navigation
import com.yunjung.sample.presentation.splash.SplashFragment
import com.yunjung.sample.util.Logger
import com.yunjung.sample.util.extension.setStatusBarTransparent
import com.yunjung.sample.util.extension.showToast

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

        // BackButton 동작 설정
        setBackButton()
    }

    /**
     * BackButton 동작 설정
     * */
    private var checkTime = 0L
    private fun setBackButton(){
        val backPressedDispatcher = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val visibleFragment = Navigation.getLastFragment()
                if(visibleFragment is SplashFragment) return // Splash 백키 막음

                if(System.currentTimeMillis() - checkTime < 2000){
                    appFinish()
                }else {
                    checkTime = System.currentTimeMillis()
                    showToast(getString(R.string.back_button_pressed_message))
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, backPressedDispatcher)
    }

    /**
     * 앱 종료
     * */
    private fun appFinish(){
        finish()
    }
}