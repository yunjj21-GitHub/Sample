package com.yunjung.sample.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseViewModel
import com.yunjung.sample.domain.model.Advertisement

class HomeViewModel: BaseViewModel() {
    private val _adList = MutableLiveData<List<Advertisement>>()
    val adList: LiveData<List<Advertisement>> get() = _adList

    fun updateAdList(){
        _adList.value = arrayListOf(
            Advertisement(R.color.ad1_left, R.color.ad1_right, R.drawable.img_ad1, R.string.home_ad_summary1, R.string.home_ad_content1),
            Advertisement(R.color.ad2_left, R.color.ad2_right, R.drawable.img_ad2, R.string.home_ad_summary2, R.string.home_ad_content2),
            Advertisement(R.color.ad3_left, R.color.ad3_right, R.drawable.img_ad3, R.string.home_ad_summary3, R.string.home_ad_content3),
            Advertisement(R.color.ad4_left, R.color.ad4_right, R.drawable.img_ad4, R.string.home_ad_summary4, R.string.home_ad_content4),
            Advertisement(R.color.ad5_left, R.color.ad5_right, R.drawable.img_ad5, R.string.home_ad_summary5, R.string.home_ad_content5),
            Advertisement(R.color.ad6_left, R.color.ad6_right, R.drawable.img_ad6, R.string.home_ad_summary6, R.string.home_ad_content6)
        )
    }
}