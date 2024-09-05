package com.yunjung.sample.presentation.login.manager

import android.content.Context
import android.widget.Button
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yunjung.sample.domain.model.User
import com.yunjung.sample.util.Logger

class KakaoLoginManager(
    private val context: Context?,
    loginBtn: Button
) {
    private lateinit var loginSuccessListener: (User) -> Unit

    init {
        setLoginBtn(loginBtn)
    }

    private fun setLoginBtn(loginBtn: Button){
        loginBtn.setOnClickListener {
            loginWithKaKao()
        }
    }

    fun setLoginSuccessListener(callback: (User) -> Unit) {
        loginSuccessListener = callback
    }

    private fun loginWithKaKao(){
        val loginWithKakaoAccountCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Logger.d("카카오 계정 로그인 시도")
            if(error != null) {
                Logger.e("카카오 계정 로그인 실패 -> $error")
            }else if(token != null) {
                getProfile()
            }
        }

        context?.let {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                // 카카오톡 로그인
                Logger.d("카카오톡 로그인 시도")
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if(error != null) {
                        Logger.e("카카오톡 로그인 실패 -> $error")

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(
                            context,
                            callback = loginWithKakaoAccountCallback
                        )
                    }else if(token != null){
                        getProfile()
                    }
                }
            }else {
                // 카카오 계정 로그인
                UserApiClient.instance.loginWithKakaoAccount(
                    context,
                    callback = loginWithKakaoAccountCallback
                )
            }
        }
    }

    private fun getProfile(){
        UserApiClient.instance.me { user, error ->
            if(error != null){
                Logger.e("카카오 사용자 프로필 조회 실패 -> $error")
            }else if(user != null) {
                user.kakaoAccount?.apply {
                    val brith = "${user.kakaoAccount?.birthyear}-${user.kakaoAccount?.birthday}"
                    val profileImage = user.kakaoAccount?.profile?.profileImageUrl
                    loginSuccessListener(User(email, gender.toString(), brith, profileImage, phoneNumber))
                }
            }
        }
    }
}