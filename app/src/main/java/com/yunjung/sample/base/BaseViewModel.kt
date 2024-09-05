package com.yunjung.sample.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    /** App 에서 발생한 loading (API 통신, 기타 작업 등) */
    protected open val _appLoading = MutableLiveData<Boolean>()
    val appLoading: LiveData<Boolean> get() = _appLoading

    /** Web 에서 발생한 loading */
    protected open val _webLoading = MutableLiveData<Boolean>()
    val webLoading: LiveData<Boolean> get() = _webLoading

    fun showAppLoading(){
        _appLoading.postValue(true)
    }

    fun hideAppLoading(){
        _appLoading.postValue(false)
    }

    fun showWebLoading(){
        _webLoading.postValue(true)
    }

    fun hideWebLoading(){
        _webLoading.postValue(false)
    }
}