package com.yunjung.sample

import android.app.Application
import com.navercorp.nid.NaverIdLoginSDK
import com.yunjung.sample.constanct.Naver
import com.yunjung.sample.presentation.login.manager.NaverLoginManager

class SampleApplication: Application() {
    companion object {
        lateinit var INSTANCE: SampleApplication
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        NaverIdLoginSDK.initialize(
            this,
            Naver.OAUTH_CLIENT_ID,
            Naver.OAUTH_CLIENT_SECRET,
            Naver.OAUTH_CLIENT_NAME
        )
    }
}