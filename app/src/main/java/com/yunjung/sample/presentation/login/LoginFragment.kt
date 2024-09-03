package com.yunjung.sample.presentation.login

import android.annotation.SuppressLint
import android.text.InputType
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentLoginBinding
import com.yunjung.sample.presentation.login.manager.NaverLoginManager
import com.yunjung.sample.util.extension.showToast

class LoginFragment: BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var naverLoginManager: NaverLoginManager

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        binding.pwVisibilityBtn.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> setPwVisibility(false)
                MotionEvent.ACTION_DOWN -> setPwVisibility(true)
            }
            true
        }

        // 네이버 로그인 버튼 설정
        naverLoginManager = NaverLoginManager(context)
        naverLoginManager.setLoginBtn(binding.naverLoginBtn)
        naverLoginManager.setLoginSuccessListener {user ->
            activity.showToast(user.toString())
        }
    }

    override fun initObserver() {
        super.initObserver()
    }

    private fun setPwVisibility(isVisible: Boolean) {
        if(isVisible){
            binding.pwVisibilityBtn.background =
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_visibility_blue) }
            binding.pwEdTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }else{
            binding.pwVisibilityBtn.background =
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_visibility_gray) }
            binding.pwEdTxt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        binding.pwEdTxt.setSelection(binding.pwEdTxt.text.length)
    }
}