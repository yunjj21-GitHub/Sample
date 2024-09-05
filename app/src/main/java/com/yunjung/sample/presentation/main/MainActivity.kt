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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        initObserver()
    }

    private fun initObserver(){
        viewModel.appLoading.observe(this){
            setLoading()
        }
        viewModel.webLoading.observe(this){
            setLoading()
        }
    }

    private var loadingCheckJob: Job? = null
    private fun setLoading(){
        if(viewModel.appLoading.value == true || viewModel.webLoading.value == true){
            binding.loading.show()

            // loading 이 노출되었을 때, 앱 크래시가 발생하는 현상 대응
            loadingCheckJob?.cancel()
            loadingCheckJob = CoroutineScope(Dispatchers.Default).launch {
                delay(10*1000) // 10초
                viewModel.hideAppLoading()
                viewModel.hideWebLoading()
            }.also{
                it.start()
            }
        } else {
            binding.loading.hide()
        }
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
                // loading 노출 시 백키 막음
                if(viewModel.appLoading.value == true || viewModel.webLoading.value == true) return

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