package com.yunjung.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yunjung.sample.presentation.main.MainActivity
import com.yunjung.sample.presentation.main.MainViewModel

abstract class BaseFragment<B: ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int
): Fragment() {
    val TAG = this::class.java.simpleName

    protected lateinit var binding: B

    abstract val viewModel: BaseViewModel
    val mainViewModel: MainViewModel by activityViewModels()
    val activity: MainActivity by lazy { requireActivity() as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        initObserver()

        // Loading 이벤트 메인으로 전달
        viewModel.appLoading.observe(viewLifecycleOwner) {
            if(it) mainViewModel.showAppLoading()
            else mainViewModel.hideAppLoading()
        }
        viewModel.webLoading.observe(viewLifecycleOwner) {
            if(it) mainViewModel.showWebLoading()
            else mainViewModel.hideWebLoading()
        }
    }

    abstract fun initView()
    open fun initObserver() {}
}