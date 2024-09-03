package com.yunjung.sample.presentation.login.manager

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.yunjung.sample.R
import com.yunjung.sample.domain.model.User
import com.yunjung.sample.util.Logger

// Naver Developers: https://developers.naver.com/docs/login/android/android.md
// 테스트 계정 등록: Application > 내 애플리케이션 > 멤버관리
class NaverLoginManager(private val context: Context?) {
    private lateinit var loginSuccessListener: (User) -> Unit
    private lateinit var oauthLoginCallback: OAuthLoginCallback
    private lateinit var nidProfileCallback: NidProfileCallback<NidProfileResponse>
    private lateinit var deleteTokenCallback: OAuthLoginCallback

    init {
        initOAuthLoginCallback()
        initNidProfileCallback()
        initDeleteTokenCallback()
    }

    fun setLoginBtn(loginBtn: NidOAuthLoginButton){
        loginBtn.setOAuthLogin(oauthLoginCallback)
        // Naver Login Button 기본 이미지 제거
        context?.let {
            loginBtn.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.ic_login_naver))
        }
    }

    private fun initOAuthLoginCallback(){
        oauthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Logger.d("OAuthLoginCallback onError -> $errorCode, $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Logger.d("OAuthLoginCallback onFailure -> $httpStatus, $message")
            }

            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(nidProfileCallback)
            }
        }
    }

    private fun initNidProfileCallback(){
        nidProfileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                Logger.d("NidProfileCallback onError -> $errorCode, $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Logger.d("NidProfileCallback onFailure -> $httpStatus, $message")
            }

            override fun onSuccess(result: NidProfileResponse) {
                getProfile(result)
            }
        }
    }

    private fun initDeleteTokenCallback(){
        deleteTokenCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Logger.d("DeleteTokenCallback onError -> $errorCode, $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Logger.d("DeleteTokenCallback onFailure -> $httpStatus, $message")
            }

            override fun onSuccess() {
                Logger.d("DeleteTokenCallback onSuccess")
            }
        }
    }

    private fun getProfile(result: NidProfileResponse){
        result.profile?.apply {
            val user = User(email, gender, "$birthYear-$birthday", profileImage, mobile)
            loginSuccessListener(user)
        }
        NidOAuthLogin().callDeleteTokenApi(deleteTokenCallback)
    }

    fun setLoginSuccessListener(callback: (User) -> Unit){
        loginSuccessListener = callback
    }
}

/**
 * 응답값 NidProfileResponse.profile 의 예
 * {
 *   "id": "w6IZ0s3jHPipBFzHb517sasYTgjiGgyXVaa8RY3lhX0",
 *   "nickname": "수성",
 *   "name": "박윤정",
 *   "email": "puj98@naver.com",
 *   "gender": "F",
 *   "age": "20-29",
 *   "birthday": "09-21",
 *   "profileImage": "https://phinf.pstatic.net/contac/20231018_87/16975870220307k8EJ_JPEG/12345.jpg",
 *   "birthYear": "1998",
 *   "mobile": "010-2615-9179",
 *   "ci": null,
 *   "encId": null
 * }
 * */