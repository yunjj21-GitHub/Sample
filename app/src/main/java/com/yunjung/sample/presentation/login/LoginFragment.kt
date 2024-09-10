package com.yunjung.sample.presentation.login

import android.annotation.SuppressLint
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentLoginBinding
import com.yunjung.sample.navigation.NavDest
import com.yunjung.sample.navigation.Navigation
import com.yunjung.sample.presentation.login.manager.GoogleLoginManager
import com.yunjung.sample.presentation.login.manager.KakaoLoginManager
import com.yunjung.sample.presentation.login.manager.NaverLoginManager
import com.yunjung.sample.util.extension.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment: BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var naverLoginManager: NaverLoginManager
    private lateinit var kakaoLoginManager: KakaoLoginManager
    private lateinit var googleLoginManager: GoogleLoginManager

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        binding.emailEdTxt.addTextChangedListener {
            if(binding.emailEdTxt.text.isEmpty()){
                binding.emailClearBtn.visibility = View.GONE
            }else {
                binding.emailClearBtn.visibility = View.VISIBLE
            }
        }

        binding.emailClearBtn.setOnClickListener {
            binding.emailEdTxt.text.clear()
        }

        binding.pwVisibilityBtn.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> setPwVisibility(false)
                MotionEvent.ACTION_DOWN -> setPwVisibility(true)
            }
            true
        }

        // 이메일 로그인 버튼 설정
        binding.emailLoginBtn.setOnClickListener {
            activity.navigation.navigate(NavDest.home)
        }

        // 네이버 로그인 버튼 설정
        naverLoginManager = NaverLoginManager(context, binding.naverLoginBtn)
        naverLoginManager.setLoginSuccessListener { user ->
            activity.showToast(user.toString())
            activity.navigation.navigate(NavDest.home)
        }

        // 카카오 로그인 버튼 설정
        kakaoLoginManager = KakaoLoginManager(context, binding.kakaoLoginBtn)
        kakaoLoginManager.setLoginSuccessListener { user ->
            activity.showToast(user.toString())
            activity.navigation.navigate(NavDest.home)
        }

        // 구글 로그인 버튼 설정
        googleLoginManager = GoogleLoginManager(context, binding.googleLoginBtn)
        googleLoginManager.setLoginSuccessListener { user ->
            CoroutineScope(Dispatchers.Main).launch {
                activity.showToast(user.toString())
                activity.navigation.navigate(NavDest.home)
            }
        }

        // TODO: 로그인 유지, 비밀번호 찾기, 회원가입, 이메일 찾기 기능 구현 필요함
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